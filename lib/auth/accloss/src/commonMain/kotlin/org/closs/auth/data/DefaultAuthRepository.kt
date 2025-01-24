package org.closs.auth.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.closs.auth.shared.data.AuthRepository
import org.closs.core.api.shared.client.ApiOperation
import org.closs.core.api.shared.client.KtorClient
import org.closs.core.api.shared.client.call
import org.closs.core.api.shared.client.get
import org.closs.core.api.shared.client.post
import org.closs.core.database.helper.ACCLOSSDbHelper
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.please_log_in
import org.closs.core.resources.resources.generated.resources.welcome_back
import org.closs.core.types.auth.dbAccountsToDomain
import org.closs.core.types.salesman.Salesman
import org.closs.core.types.salesman.dto.SalesmanDto
import org.closs.core.types.salesman.toDbSalesman
import org.closs.core.types.salesman.toSalesman
import org.closs.core.types.shared.auth.Session
import org.closs.core.types.shared.auth.dto.AuthDto
import org.closs.core.types.shared.auth.dto.ForgotPasswordDto
import org.closs.core.types.shared.auth.dto.SignInDto
import org.closs.core.types.shared.auth.dtoToDomain
import org.closs.core.types.shared.auth.sessionToDb
import org.closs.core.types.shared.state.DataCodes
import org.closs.core.types.shared.state.RequestState
import org.closs.core.types.shared.state.ResponseMessage
import org.closs.core.types.shared.user.domainToDb
import org.closs.core.types.shared.user.dto.UserDto
import org.closs.core.types.shared.user.dtoToDomain
import kotlin.coroutines.CoroutineContext

class DefaultAuthRepository(
    private val client: KtorClient,
    private val dbHelper: ACCLOSSDbHelper,
    override val coroutineContext: CoroutineContext,
    override val scope: CoroutineScope,
) : AuthRepository {
    override suspend fun signIn(
        baseUrl: String?,
        signInDto: SignInDto
    ): Flow<RequestState<DataCodes>> = flow {
        emit(RequestState.Loading)

        if (baseUrl == null) {
            return@flow emit(
                RequestState.Error(
                    error = "empty base url"
                )
            )
        }

        val call = client.call<AuthDto> {
            post(
                baseUrl = baseUrl,
                urlString = "/api/auth/signIn",
                body = signInDto
            )
        }

        when (call) {
            is ApiOperation.Failure -> {
                emit(
                    RequestState.Error(
                        error = call.error.message ?: ""
                    )
                )
            }
            is ApiOperation.Success -> {
                val data = call.value.data
                    ?: return@flow emit(
                        RequestState.Error(
                            error = ""
                        )
                    )
                val session = data.dtoToDomain()

                when (val userCall = getUserData(baseUrl, session)) {
                    is RequestState.Error -> {
                        emit(
                            RequestState.Error(
                                error = userCall.error
                            )
                        )
                    }
                    is RequestState.Success -> {
                        emit(
                            RequestState.Success(
                                data = DataCodes.CustomMessage(
                                    res = ResponseMessage(
                                        message = Res.string.welcome_back,
                                        description = session.name
                                    )
                                )
                            )
                        )
                    }
                    else -> {
                        emit(
                            RequestState.Loading
                        )
                    }
                }
            }
        }
    }

    override suspend fun forgotPassword(
        baseUrl: String?,
        forgotPasswordDto: ForgotPasswordDto
    ): RequestState<DataCodes> {
        val call = client.call<AuthDto> {
            post(
                baseUrl = baseUrl,
                urlString = "/api/auth/forgotPassword",
                body = forgotPasswordDto
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

                RequestState.Success(
                    data = DataCodes.CustomMessage(
                        res = ResponseMessage(
                            message = Res.string.please_log_in,
                        )
                    )
                )
            }
        }
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

    override suspend fun startSession(id: String) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.transaction {
                    db.clossSessionQueries.endSessions(id)
                    db.clossSessionQueries.startSession(
                        id = id
                    )
                }
            }
        }.await()
    }

    // refactor this sht
    private suspend fun getUserData(baseUrl: String?, session: Session): RequestState<Session> {
        val call = client.call<UserDto> {
            get(
                baseUrl = baseUrl,
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
}
