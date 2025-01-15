package org.closs.auth.data.repository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.closs.auth.shared.data.repository.AuthRepository
import org.closs.core.api.KtorClient
import org.closs.core.database.helper.PickingDbHelper
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.please_log_in
import org.closs.core.resources.resources.generated.resources.unknown_error
import org.closs.core.resources.resources.generated.resources.welcome_back
import org.closs.core.types.shared.auth.Session
import org.closs.core.types.shared.auth.dto.AuthDto
import org.closs.core.types.shared.auth.dto.ForgotPasswordDto
import org.closs.core.types.shared.auth.dto.SignInDto
import org.closs.core.types.shared.auth.dtoToDomain
import org.closs.core.types.shared.auth.sessionToDb
import org.closs.core.types.shared.response.display
import org.closs.core.types.shared.state.DataCodes
import org.closs.core.types.shared.state.RequestState
import org.closs.core.types.shared.user.domainToDb
import kotlin.coroutines.CoroutineContext

class DefaultAuthRepository(
    private val ktorClient: KtorClient,
    private val dbHelper: PickingDbHelper,
    override val coroutineContext: CoroutineContext,
    override val scope: CoroutineScope,
) : AuthRepository {
    override suspend fun signIn(signInDto: SignInDto): RequestState<DataCodes> {
        val call = ktorClient.call<AuthDto> {
            post(
                urlString = "/api/auth/signIn",
                body = signInDto
            )
        }

        return call.display(
            onFailure = { code ->
                RequestState.Error(error = code)
            },
            onSuccess = { res ->
                if (res.data == null) {
                    return@display RequestState.Error(
                        error = DataCodes.NullError(
                            msg = Res.string.unknown_error,
                            desc = res.message
                        )
                    )
                }

                saveSession(res.data!!.dtoToDomain())

                RequestState.Success(
                    data = DataCodes.CustomMessage(
                        msg = Res.string.welcome_back,
                        desc = res.message
                    )
                )
            }
        )
    }

    override suspend fun forgotPassword(forgotPasswordDto: ForgotPasswordDto): RequestState<DataCodes> {
        val call = ktorClient.call<AuthDto> {
            post(
                urlString = "/api/auth/forgotPassword",
                body = forgotPasswordDto
            )
        }

        return call.display(
            onFailure = { code ->
                RequestState.Error(
                    error = code
                )
            },
            onSuccess = { res ->
                if (res.data == null) {
                    return@display RequestState.Error(
                        error = DataCodes.NullError(
                            msg = Res.string.unknown_error,
                            desc = res.message
                        )
                    )
                }
                saveSession(res.data!!.dtoToDomain())

                RequestState.Success(
                    data = DataCodes.CustomMessage(
                        msg = Res.string.please_log_in,
                        desc = res.message
                    )
                )
            }
        )
    }

    override fun getAccounts(): Flow<RequestState<List<Session>>> {
        return emptyFlow()
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

    private suspend fun saveSession(session: Session) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.transaction {
                    db.clossSessionQueries.insert(
                        closs_session = session.sessionToDb()
                    )
                        .executeAsOneOrNull()
                        ?: rollback()

                    db.clossUserQueries.insert(
                        closs_user = session.user.domainToDb()
                    )
                        .executeAsOneOrNull()
                        ?: rollback()
                }
            }
        }.await()
    }
}
