package org.closs.auth.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.closs.auth.shared.data.repository.AuthRepository
import org.closs.core.api.shared.KtorClient
import org.closs.core.api.shared.call
import org.closs.core.api.shared.get
import org.closs.core.api.shared.post
import org.closs.core.database.helper.PickingDbHelper
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.please_log_in
import org.closs.core.resources.resources.generated.resources.unexpected_error
import org.closs.core.resources.resources.generated.resources.unknown_error
import org.closs.core.resources.resources.generated.resources.welcome_back
import org.closs.core.types.picker.Picker
import org.closs.core.types.picker.dto.PickerDto
import org.closs.core.types.picker.toDbPicker
import org.closs.core.types.picker.toPicker
import org.closs.core.types.shared.auth.Session
import org.closs.core.types.shared.auth.dto.AuthDto
import org.closs.core.types.shared.auth.dto.ForgotPasswordDto
import org.closs.core.types.shared.auth.dto.SignInDto
import org.closs.core.types.shared.auth.dtoToDomain
import org.closs.core.types.shared.auth.sessionToDb
import org.closs.core.types.shared.response.ApiOperation
import org.closs.core.types.shared.state.DataCodes
import org.closs.core.types.shared.state.RequestState
import org.closs.core.types.shared.state.ResponseMessage
import org.closs.core.types.shared.user.domainToDb
import org.closs.core.types.shared.user.dto.UserDto
import org.closs.core.types.shared.user.dtoToDomain
import kotlin.coroutines.CoroutineContext

class DefaultAuthRepository(
    private val client: KtorClient,
    private val dbHelper: PickingDbHelper,
    override val coroutineContext: CoroutineContext,
    override val scope: CoroutineScope,
) : AuthRepository {
    override suspend fun signIn(signInDto: SignInDto): Flow<RequestState<DataCodes>> = flow {
        emit(RequestState.Loading)
        val call = client.call<AuthDto> {
            post(
                urlString = "/api/auth/signIn",
                body = signInDto
            )
        }

        when (call) {
            is ApiOperation.Failure -> {
                emit(
                    RequestState.Error(
                        error = call.error.response.copy(
                            message = Res.string.unexpected_error
                        )
                    )
                )
            }
            is ApiOperation.Success -> {
                val data = call.value.data
                    ?: return@flow emit(
                        RequestState.Error(
                            error = DataCodes.NullError().response.copy(
                                message = Res.string.unknown_error,
                            )
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
            else -> {
                emit(
                    RequestState.Loading
                )
            }
        }
    }.flowOn(coroutineContext)

    override suspend fun forgotPassword(forgotPasswordDto: ForgotPasswordDto): RequestState<DataCodes> {
        val call = client.call<AuthDto> {
            post(
                urlString = "/api/auth/forgotPassword",
                body = forgotPasswordDto
            )
        }

        return when (call) {
            is ApiOperation.Failure -> {
                RequestState.Error(
                    error = call.error.response.copy(
                        message = Res.string.unexpected_error
                    )
                )
            }
            is ApiOperation.Success -> {
                if (call.value.data == null) {
                    return RequestState.Error(
                        error = DataCodes.NullError(
                            res = ResponseMessage(
                                message = Res.string.unknown_error,
                                description = call.value.message
                            )
                        ).response
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

    // refactor this sht
    private suspend fun getUserData(session: Session): RequestState<Session> {
        val call = client.call<UserDto> {
            get(
                urlString = "/api/users/me",
                headers = mapOf("Authorization" to "Bearer ${session.accessToken}")
            )
        }

        return when (call) {
            is ApiOperation.Failure -> {
                RequestState.Error(
                    error = call.error.response.copy(
                        message = Res.string.unexpected_error
                    )
                )
            }
            is ApiOperation.Success -> {
                if (call.value.data == null) {
                    return RequestState.Error(
                        error = DataCodes.NullError(
                            res = ResponseMessage(
                                message = Res.string.unknown_error,
                                description = call.value.message
                            )
                        ).response
                    )
                }

                val infoCall = client.call<PickerDto> {
                    get(
                        urlString = "/api/users/me/info",
                        headers = mapOf("Authorization" to "Bearer ${session.accessToken}")
                    )
                }

                when (infoCall) {
                    is ApiOperation.Failure -> {
                        RequestState.Error(
                            error = DataCodes.NullError(
                                res = ResponseMessage(
                                    message = Res.string.unknown_error,
                                    description = call.value.message
                                )
                            ).response
                        )
                    }
                    is ApiOperation.Success -> {
                        if (infoCall.value.data == null) {
                            return RequestState.Error(
                                error = DataCodes.NullError(
                                    res = ResponseMessage(
                                        message = Res.string.unknown_error,
                                        description = call.value.message
                                    )
                                ).response
                            )
                        }

                        val newSession = saveSession(
                            session = session.copy(user = call.value.data?.dtoToDomain()),
                            picker = infoCall.value.data!!.toPicker()
                        )
                            ?: return RequestState.Error(
                                error = DataCodes.NullError(
                                    res = ResponseMessage(
                                        message = Res.string.unknown_error,
                                        description = call.value.message
                                    )
                                ).response
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
                        .executeAsOneOrNull()
                        ?: rollback(null)

                    db.clossUserQueries.insert(
                        closs_user = session.user!!.domainToDb()
                    )
                        .executeAsOneOrNull()
                        ?: rollback(null)

                    db.clossPickerQueries.insert(
                        closs_picker = picker.toDbPicker(session.user!!.id)
                    )
                        .executeAsOneOrNull()
                        ?: rollback(null)

                    session
                }
            }
        }.await()
    }
}
