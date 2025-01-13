package org.closs.accloss.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.closs.core.api.KtorClient
import org.closs.core.database.helper.DbHelper
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.session_expired
import org.closs.core.resources.resources.generated.resources.unexpected_error
import org.closs.core.resources.resources.generated.resources.unknown_error
import org.closs.core.resources.resources.generated.resources.welcome
import org.closs.core.shared.types.auth.AuthDto
import org.closs.core.shared.types.auth.RefreshTokenDto
import org.closs.core.types.data.Repository
import org.closs.core.types.auth.Session
import org.closs.core.types.auth.dbAccountsToDomain
import org.closs.core.types.auth.dbActiveToDomain
import org.closs.core.types.auth.dtoToDomain
import org.closs.core.types.auth.sessionToDb
import org.closs.core.types.response.ApiOperation
import org.closs.core.types.response.display
import org.closs.core.types.state.DataCodes
import org.closs.core.types.state.RequestState
import org.closs.core.types.user.domainToDb
import kotlin.coroutines.CoroutineContext

interface AppRepository : Repository {
    fun validateSession(): Flow<RequestState<Session>>
    fun getAccounts(): Flow<RequestState<List<Session>>>
}

class DefaultAppRepository(
    private val ktorClient: KtorClient,
    private val dbHelper: DbHelper,
    override val coroutineContext: CoroutineContext,
    override val scope: CoroutineScope,
) : AppRepository {
    override fun validateSession(): Flow<RequestState<Session>> {
        return flow {
            emit(RequestState.Loading)

            val session = dbHelper.withDatabase { db ->
                executeOne(
                    query = db.clossSessionQueries.findActiveAccount()
                )
            }

            if (session != null) {
                val refreshCall = refresh(session.refresh_token).display(
                    onFailure = { code ->
                        endSession()
                        RequestState.Error(
                            error = code
                        )
                    },
                    onSuccess = { res ->
                        if (res.data == null) {
                            endSession()
                            return@display RequestState.Error(
                                error = DataCodes.NullError(
                                    msg = Res.string.session_expired,
                                    desc = res.message
                                )
                            )
                        }
                        handleSuccessRefresh(
                            session = res.data!!.dtoToDomain(),
                        )
                    }
                )
                emit(refreshCall)
            }
            emit(
                RequestState.Error(
                    error = DataCodes.NullError(
                        desc = "Session is null"
                    )
                )
            )
        }.flowOn(coroutineContext)
    }

    override fun getAccounts(): Flow<RequestState<List<Session>>> {
        return flow {
            emit(RequestState.Loading)
            dbHelper.withDatabase { db ->
                executeListAsFlow(
                    query = db.clossSessionQueries.findAccounts()
                )
            }.collect { list ->
                if (list.isEmpty()) {
                    return@collect emit(
                        RequestState.Error(
                            error = DataCodes.NullError(
                                msg = Res.string.welcome,
                            )
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

    private suspend fun refresh(refreshToken: String): ApiOperation<AuthDto> {
        return ktorClient.call {
            post(
                urlString = "/api/auth/refresh",
                body = RefreshTokenDto(
                    refreshToken = refreshToken
                )
            )
        }
    }

    private suspend fun handleSuccessRefresh(
        session: Session,
    ): RequestState<Session> {
        scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    db.clossUserQueries.insert(
                        closs_user = session.user.domainToDb()
                    )
                        .executeAsOneOrNull()
                        ?: rollback(null)

                    db.clossSessionQueries.insert(
                        closs_session = session.copy(active = true).sessionToDb()
                    )
                        .executeAsOneOrNull()
                        ?: rollback(null)
                }
            }
        }.await()
            ?: return RequestState.Error(
                error = DataCodes.NullError(
                    msg = Res.string.unexpected_error,
                    desc = "Unable to update session"
                )
            )

        val newSession = dbHelper.withDatabase { db ->
            executeOne(
                query = db.clossSessionQueries.findActiveAccount()
            )
        }
            ?: return RequestState.Error(
                error = DataCodes.NullError(
                    msg = Res.string.unknown_error,
                    desc = "Unable to find session"
                )
            )

        return RequestState.Success(newSession.dbActiveToDomain())
    }

    private suspend fun endSession() {
        dbHelper.withDatabase { db ->
            db.transaction {
                db.clossSessionQueries.endSession()
            }
        }
    }
}
