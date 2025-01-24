package org.closs.accloss.app.data

import dev.tmapps.konnection.Konnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.closs.shared.app.data.AppRepository
import org.closs.core.api.shared.client.KtorClient
import org.closs.core.api.shared.client.call
import org.closs.core.api.shared.client.get
import org.closs.core.api.shared.client.post
import org.closs.core.database.helper.ACCLOSSDbHelper
import org.closs.core.types.auth.dbAccountsToDomain
import org.closs.core.types.auth.dbActiveToDomain
import org.closs.core.types.salesman.Salesman
import org.closs.core.types.salesman.dto.SalesmanDto
import org.closs.core.types.salesman.toDbSalesman
import org.closs.core.types.salesman.toSalesman
import org.closs.core.types.shared.auth.Session
import org.closs.core.types.shared.auth.dto.AuthDto
import org.closs.core.types.shared.auth.dto.RefreshTokenDto
import org.closs.core.types.shared.auth.dtoToDomain
import org.closs.core.types.shared.auth.sessionToDb
import org.closs.core.api.shared.client.ApiOperation
import org.closs.core.types.shared.state.AppCodes
import org.closs.core.types.shared.state.RequestState
import org.closs.core.types.shared.user.domainToDb
import org.closs.core.types.shared.user.dto.UserDto
import org.closs.core.types.shared.user.dtoToDomain
import kotlin.coroutines.CoroutineContext

class DefaultAppRepository(
    private val client: KtorClient,
    private val dbHelper: ACCLOSSDbHelper,
    private val konnection: Konnection,
    override val coroutineContext: CoroutineContext,
    override val scope: CoroutineScope,
) : AppRepository {
    override fun validateSession(): Flow<RequestState<Session>> {
        return flow {
            emit(RequestState.Loading)

            dbHelper.withDatabase { db ->
                executeOneAsFlow(
                    query = db.sessionQueries.findActiveAccount()
                )
            }.catch { e ->
                emit(
                    RequestState.Error(
                        error = e.message ?: ""
                    )
                )
            }.collect { session ->
                if (session == null) {
                    return@collect emit(
                        RequestState.Error(
                            error = ""
                        )
                    )
                }
                if (konnection.isConnected()) {
                    when (val call = refresh(session.dbActiveToDomain())) {
                        is RequestState.Error -> {
                            endSession()
                            emit(
                                RequestState.Error(
                                    error = call.error
                                )
                            )
                        }
                        is RequestState.Success -> {
                            emit(
                                RequestState.Success(
                                    data = call.data
                                )
                            )
                        }
                        else -> {
                            emit(RequestState.Loading)
                        }
                    }
                }
            }
        }.flowOn(coroutineContext)
    }

    override fun getAccounts(): Flow<RequestState<List<Session>>> {
        return flow {
            emit(RequestState.Loading)
            dbHelper.withDatabase { db ->
                executeListAsFlow(
                    query = db.sessionQueries.findAccounts()
                )
            }.collect { list ->
                if (list.isEmpty()) {
                    return@collect emit(
                        RequestState.Error(
                            error = ""
                        )
                    )
                }

                emit(
                    RequestState.Success(
                        data = list.map { session ->
                            session.dbAccountsToDomain()
                        }
                    )
                )
            }
        }.flowOn(coroutineContext)
    }

    // todo: this needs to check company data first before refreshing
    private suspend fun refresh(session: Session): RequestState<Session> {
        val refreshCall = client.call<AuthDto> {
            post(
                baseUrl = session.companyHost,
                urlString = "/api/auth/refresh",
                body = RefreshTokenDto(
                    refreshToken = session.refreshToken
                ),
                headers = mapOf("Authorization" to "Bearer ${session.accessToken}")
            )
        }
        return when (refreshCall) {
            is ApiOperation.Failure -> when (refreshCall.error.status) {
                AppCodes.UnknownError.value,
                AppCodes.InternalServerError.value,
                AppCodes.ServiceUnavailable.value,
                AppCodes.RequestTimeout.value -> {
                    return RequestState.Success(
                        data = session
                    )
                }
                else -> {
                    return RequestState.Error(
                        error = refreshCall.error.message ?: ""
                    )
                }
            }
            is ApiOperation.Success -> {
                if (refreshCall.value.data == null) {
                    return RequestState.Error(
                        error = refreshCall.value.message ?: ""
                    )
                }

                getUserData(
                    session = refreshCall.value.data!!.dtoToDomain(),
                )
            }
        }
    }

// refactor this sht
    private suspend fun getUserData(session: Session): RequestState<Session> {
        val call = client.call<UserDto> {
            get(
                baseUrl = session.companyHost,
                urlString = "/api/users/me",
                headers = mapOf("Authorization" to "Bearer ${session.accessToken}")
            )
        }

        return when (call) {
            is ApiOperation.Failure -> {
                RequestState.Error(
                    error = call.error.message ?: ""
                )
            }
            is ApiOperation.Success -> {
                if (call.value.data == null) {
                    return RequestState.Error(
                        error = call.value.message ?: ""
                    )
                }

                val infoCall = client.call<SalesmanDto> {
                    get(
                        urlString = "/api/users/me/info",
                        headers = mapOf("Authorization" to "Bearer ${session.accessToken}")
                    )
                }

                when (infoCall) {
                    is ApiOperation.Failure -> {
                        RequestState.Error(
                            error = infoCall.error.message ?: ""
                        )
                    }
                    is ApiOperation.Success -> {
                        if (infoCall.value.data == null) {
                            return RequestState.Error(
                                error = infoCall.value.message ?: ""
                            )
                        }

                        val newSession = saveSession(
                            session = session.copy(user = call.value.data?.dtoToDomain()),
                            salesman = infoCall.value.data!!.toSalesman()
                        )
                            ?: return RequestState.Error(
                                error = ""
                            )

                        RequestState.Success(
                            data = newSession
                        )
                    }
                }
            }
        }
    }

    private suspend fun saveSession(session: Session, salesman: Salesman): Session? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    if (session.user == null) {
                        rollback(null)
                    }

                    db.clossSessionQueries.insert(
                        closs_session = session.copy(active = true).sessionToDb()
                    )

                    db.clossUserQueries.insert(
                        closs_user = session.user!!.domainToDb()
                    )

                    db.clossSalesmanQueries.insert(
                        closs_salesman = salesman.toDbSalesman(session.user!!.id)
                    )

                    session
                }
            }
        }.await()
    }

    private suspend fun endSession() {
        dbHelper.withDatabase { db ->
            db.transaction {
                db.clossSessionQueries.endSession()
            }
        }
    }
}
