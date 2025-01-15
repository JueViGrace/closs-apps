package org.closs.accloss.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.closs.app.shared.data.AppRepository
import org.closs.core.api.KtorClient
import org.closs.core.database.helper.ACCLOSSDbHelper
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.session_expired
import org.closs.core.resources.resources.generated.resources.unexpected_error
import org.closs.core.resources.resources.generated.resources.unknown_error
import org.closs.core.types.shared.auth.Session
import org.closs.core.types.shared.auth.dbAccountsToDomain
import org.closs.core.types.shared.auth.dbActiveToDomain
import org.closs.core.types.shared.auth.dto.AuthDto
import org.closs.core.types.shared.auth.dto.RefreshTokenDto
import org.closs.core.types.shared.auth.dtoToDomain
import org.closs.core.types.shared.auth.sessionToDb
import org.closs.core.types.shared.response.ApiOperation
import org.closs.core.types.shared.response.display
import org.closs.core.types.shared.state.DataCodes
import org.closs.core.types.shared.state.RequestState
import org.closs.core.types.shared.user.domainToDb
import kotlin.coroutines.CoroutineContext

class DefaultAppRepository(
    private val ktorClient: KtorClient,
    private val dbHelper: ACCLOSSDbHelper,
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
                    error = DataCodes.NullError()
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
                            error = DataCodes.NullError()
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
