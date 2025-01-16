package org.closs.picking.data

import dev.tmapps.konnection.Konnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.closs.app.shared.data.AppRepository
import org.closs.core.api.shared.KtorClient
import org.closs.core.api.shared.call
import org.closs.core.api.shared.post
import org.closs.core.database.helper.PickingDbHelper
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.session_expired
import org.closs.core.resources.resources.generated.resources.unexpected_error
import org.closs.core.resources.resources.generated.resources.unknown_error
import org.closs.core.types.auth.dbActiveToDomain
import org.closs.core.types.shared.auth.Session
import org.closs.core.types.shared.auth.dto.AuthDto
import org.closs.core.types.shared.auth.dto.RefreshTokenDto
import org.closs.core.types.shared.auth.dtoToDomain
import org.closs.core.types.shared.auth.sessionToDb
import org.closs.core.types.shared.response.ApiOperation
import org.closs.core.types.shared.response.display
import org.closs.core.types.shared.state.AppCodes
import org.closs.core.types.shared.state.DataCodes
import org.closs.core.types.shared.state.RequestState
import org.closs.core.types.shared.user.domainToDb
import kotlin.coroutines.CoroutineContext

class DefaultAppRepository(
    private val client: KtorClient,
    private val dbHelper: PickingDbHelper,
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
                        error = DataCodes.UnexpectedError(
                            msg = Res.string.unexpected_error,
                            desc = e.message
                        )
                    )
                )
            }.collect { session ->
                if (session == null) {
                    return@collect emit(
                        RequestState.Error(
                            error = DataCodes.NullError()
                        )
                    )
                }
                if (konnection.isConnected()) {
                    val refreshCall = refresh(session.refresh_token).display(
                        onFailure = { code ->
                            when (code.code) {
                                AppCodes.UnknownError,
                                AppCodes.InternalServerError,
                                AppCodes.ServiceUnavailable,
                                AppCodes.RequestTimeout -> {
                                    RequestState.Success(
                                        data = session.dbActiveToDomain()
                                    )
                                }
                                else -> {
                                    endSession()
                                    RequestState.Error(
                                        error = code
                                    )
                                }
                            }
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
                    RequestState.Success(
                        data = session.dbActiveToDomain()
                    )
                )
            }
        }.flowOn(coroutineContext)
    }

    override fun getAccounts(): Flow<RequestState<List<Session>>> {
        return emptyFlow()
    }

    private suspend fun refresh(refreshToken: String): ApiOperation<AuthDto> {
        return client.call {
            post(
                urlString = "/api/auth/refresh",
                body = RefreshTokenDto(
                    refreshToken = refreshToken
                ),
                headers = mapOf("Bearer" to refreshToken)
            )
        }
    }

    private suspend fun handleSuccessRefresh(
        session: Session,
    ): RequestState<Session> {
        scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    // todo: refactor to request user data
                    if (session.user == null) {
                        rollback(null)
                    }
                    db.clossUserQueries.insert(
                        closs_user = session.user!!.domainToDb()
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
                query = db.sessionQueries.findActiveAccount()
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
