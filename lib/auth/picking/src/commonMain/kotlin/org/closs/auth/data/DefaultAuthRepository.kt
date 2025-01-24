package org.closs.auth.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.closs.auth.shared.data.AuthRepository
import org.closs.core.api.shared.auth.AuthClient
import org.closs.core.api.shared.client.ApiOperation
import org.closs.core.api.user.UserClient
import org.closs.core.database.helper.PickingDbHelper
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.please_log_in
import org.closs.core.resources.resources.generated.resources.welcome_back
import org.closs.core.types.picker.Picker
import org.closs.core.types.picker.toDbPicker
import org.closs.core.types.picker.toPicker
import org.closs.core.types.shared.auth.Session
import org.closs.core.types.shared.auth.dto.ForgotPasswordDto
import org.closs.core.types.shared.auth.dto.SignInDto
import org.closs.core.types.shared.auth.dtoToDomain
import org.closs.core.types.shared.auth.sessionToDb
import org.closs.core.types.shared.state.DataCodes
import org.closs.core.types.shared.state.RequestState
import org.closs.core.types.shared.state.ResponseMessage
import org.closs.core.types.shared.user.domainToDb
import org.closs.core.types.shared.user.dtoToDomain
import kotlin.coroutines.CoroutineContext

class DefaultAuthRepository(
    private val authClient: AuthClient,
    private val userClient: UserClient,
    private val dbHelper: PickingDbHelper,
    override val coroutineContext: CoroutineContext,
    override val scope: CoroutineScope,
) : AuthRepository {
    override suspend fun signIn(
        baseUrl: String?,
        signInDto: SignInDto
    ): Flow<RequestState<DataCodes>> = flow {
        emit(RequestState.Loading)
        when (val call = authClient.signIn(signInDto, baseUrl)) {
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

                when (val userCall = getUserData(session)) {
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
    }.flowOn(coroutineContext)

    override suspend fun forgotPassword(
        baseUrl: String?,
        forgotPasswordDto: ForgotPasswordDto
    ): RequestState<DataCodes> {
        return when (val call = authClient.forgotPassword(forgotPasswordDto, baseUrl)) {
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

    // refactor this sht
    private suspend fun getUserData(session: Session): RequestState<Session> {
        return when (val call = userClient.getUserById(session.accessToken)) {
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

                when (val infoCall = userClient.getUserInfo(session.accessToken)) {
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
                            picker = infoCall.value.data!!.toPicker()
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

    private suspend fun saveSession(session: Session, picker: Picker): Session? {
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

                    db.clossPickerQueries.insert(
                        closs_picker = picker.toDbPicker(session.user!!.id)
                    )

                    session
                }
            }
        }.await()
    }
}
