package org.closs.picking.app.data

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
import org.closs.core.api.shared.get
import org.closs.core.api.shared.post
import org.closs.core.database.helper.PickingDbHelper
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.session_expired
import org.closs.core.resources.resources.generated.resources.unexpected_error
import org.closs.core.resources.resources.generated.resources.unknown_error
import org.closs.core.resources.resources.generated.resources.welcome
import org.closs.core.types.auth.dbActiveToDomain
import org.closs.core.types.picker.Picker
import org.closs.core.types.picker.dto.PickerDto
import org.closs.core.types.picker.toDbPicker
import org.closs.core.types.picker.toPicker
import org.closs.core.types.shared.auth.Session
import org.closs.core.types.shared.auth.dto.AuthDto
import org.closs.core.types.shared.auth.dto.RefreshTokenDto
import org.closs.core.types.shared.auth.dtoToDomain
import org.closs.core.types.shared.auth.sessionToDb
import org.closs.core.types.shared.response.ApiOperation
import org.closs.core.types.shared.state.AppCodes
import org.closs.core.types.shared.state.DataCodes
import org.closs.core.types.shared.state.RequestState
import org.closs.core.types.shared.state.ResponseMessage
import org.closs.core.types.shared.user.domainToDb
import org.closs.core.types.shared.user.dto.UserDto
import org.closs.core.types.shared.user.dtoToDomain
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
                endSession()
                emit(
                    RequestState.Error(
                        error = DataCodes.UnexpectedError(
                            res = ResponseMessage(
                                message = Res.string.unexpected_error,
                                description = e.message
                            )
                        ).response
                    )
                )
            }.collect { session ->
                if (session == null) {
                    return@collect emit(
                        RequestState.Error(
                            error = DataCodes.CustomMessage(
                                res = ResponseMessage(
                                    message = Res.string.welcome
                                )
                            ).response
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
        return emptyFlow()
    }

    private suspend fun refresh(session: Session): RequestState<Session> {
        val refreshCall = client.call<AuthDto> {
            post(
                urlString = "/api/auth/refresh",
                body = RefreshTokenDto(
                    refreshToken = session.refreshToken
                ),
                headers = mapOf("Authorization" to "Bearer ${session.accessToken}")
            )
        }
        return when (refreshCall) {
            is ApiOperation.Failure -> when (refreshCall.error.code) {
                AppCodes.UnknownError,
                AppCodes.InternalServerError,
                AppCodes.ServiceUnavailable,
                AppCodes.RequestTimeout -> {
                    return RequestState.Success(
                        data = session
                    )
                }
                else -> {
                    return RequestState.Error(
                        error = refreshCall.error.response.copy(
                            message = Res.string.session_expired
                        )
                    )
                }
            }
            is ApiOperation.Success -> {
                if (refreshCall.value.data == null) {
                    return RequestState.Error(
                        error = DataCodes.NullError(
                            res = ResponseMessage(
                                message = Res.string.session_expired,
                                description = refreshCall.value.message
                            )
                        ).response
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

    private suspend fun endSession() {
        dbHelper.withDatabase { db ->
            db.transaction {
                val session = db.sessionQueries.findActiveAccount()
                    .executeAsOneOrNull()
                    ?: rollback()

                db.clossSessionQueries.deleteSession(session.user_id)
                db.clossUserQueries.delete(session.user_id)
                db.clossPickerQueries.deleteByUser(session.user_id)
                db.clossConfigQueries.deleteByUser(session.user_id)
                db.clossProductQueries.deleteByUser(session.user_id)
            }
        }
    }
}
