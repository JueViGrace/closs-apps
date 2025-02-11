package org.closs.picking.app.data

import dev.tmapps.konnection.Konnection
import io.ktor.client.plugins.observer.ResponseObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.closs.core.api.shared.auth.AuthClient
import org.closs.core.api.shared.client.ApiOperation
import org.closs.core.api.user.UserClient
import org.closs.core.database.helper.PickingDbHelper
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.invalid_state
import org.closs.core.resources.resources.generated.resources.session_expired
import org.closs.core.resources.resources.generated.resources.unexpected_error
import org.closs.core.resources.resources.generated.resources.user_info_not_found
import org.closs.core.resources.resources.generated.resources.user_not_found
import org.closs.core.types.auth.dbActiveToDomain
import org.closs.core.types.picker.Picker
import org.closs.core.types.picker.toDbPicker
import org.closs.core.types.picker.toPicker
import org.closs.core.types.shared.auth.Session
import org.closs.core.types.shared.auth.dtoToDomain
import org.closs.core.types.shared.auth.sessionToDb
import org.closs.core.types.shared.state.AppCodes
import org.closs.core.types.shared.state.RequestState
import org.closs.core.types.shared.state.ResponseMessage
import org.closs.core.types.shared.user.domainToDb
import org.closs.core.types.shared.user.dtoToDomain
import org.closs.shared.app.data.AppRepository
import kotlin.coroutines.CoroutineContext

// TODO: move konnection out
class DefaultAppRepository(
    private val authClient: AuthClient,
    private val userClient: UserClient,
    private val dbHelper: PickingDbHelper,
    private val konnection: Konnection,
    override val coroutineContext: CoroutineContext,
    override val scope: CoroutineScope,
) : AppRepository {
    override fun validateSession(): Flow<RequestState<Boolean>> = flow {
        emit(RequestState.Loading)

        dbHelper.withDatabase { db ->
            executeOneAsFlow(
                query = db.sessionQueries.findActiveAccount()
            )
        }.collect { value ->
            if (value == null) {
                return@collect emit(
                    RequestState.Error(
                        error = ResponseMessage()
                    )
                )
            }

            emit(
                RequestState.Success(
                    data = true
                )
            )
        }
    }.flowOn(coroutineContext)

    override fun getAccounts(): Flow<RequestState<List<Session>>> {
        return emptyFlow()
    }

    override fun refresh(): Flow<RequestState<Boolean>> = flow {
        emit(RequestState.Loading)
        if (!konnection.isConnected()) {
            return@flow emit(
                RequestState.Success(
                    data = true
                )
            )
        }

        val session = dbHelper.withDatabase { db ->
            executeOne(
                query = db.sessionQueries.findActiveAccount()
            )
        }?.dbActiveToDomain()
            ?: return@flow emit(
                RequestState.Error(
                    error = ResponseMessage(
                        message = Res.string.invalid_state,
                    )
                )
            )

        when (val refreshCall = authClient.refresh(refreshToken = session.refreshToken)) {
            is ApiOperation.Failure -> when (refreshCall.error.status) {
                AppCodes.UnknownError.value,
                AppCodes.InternalServerError.value,
                AppCodes.ServiceUnavailable.value,
                AppCodes.RequestTimeout.value -> {
                    emit(
                        RequestState.Success(
                            data = true
                        )
                    )
                }
                else -> {
                    endSession()
                    emit(
                        RequestState.Error(
                            error = ResponseMessage(
                                message = Res.string.session_expired,
                                description = refreshCall.error.message ?: ""
                            )
                        )
                    )
                }
            }
            is ApiOperation.Success -> {
                if (refreshCall.value.data == null) {
                    endSession()
                    emit(
                        RequestState.Error(
                            error = ResponseMessage(
                                message = Res.string.session_expired,
                                description = refreshCall.value.message ?: ""
                            )
                        )
                    )
                }

                when (
                    val result = getUserData(
                        id = session.user!!.id,
                        session = refreshCall.value.data!!.dtoToDomain(),
                    )
                ) {
                    is RequestState.Error -> {
                        endSession()
                        emit(
                            RequestState.Error(
                                error = result.error
                            )
                        )
                    }
                    is RequestState.Success -> {
                        emit(
                            RequestState.Success(
                                data = true
                            )
                        )
                    }
                    else -> {
                        emit(RequestState.Loading)
                    }
                }
            }
        }
    }

    // refactor this sht
    private suspend fun getUserData(id: String, session: Session): RequestState<Session> {
        return when (val call = userClient.getUserById(session.accessToken)) {
            is ApiOperation.Failure -> {
                RequestState.Error(
                    error = ResponseMessage(
                        message = Res.string.user_not_found,
                        description = call.error.message ?: ""
                    )
                )
            }
            is ApiOperation.Success -> {
                if (call.value.data == null) {
                    return RequestState.Error(
                        error = ResponseMessage(
                            message = Res.string.invalid_state,
                            description = call.value.message ?: ""
                        )
                    )
                }

                when (val infoCall = userClient.getUserInfo(session.accessToken)) {
                    is ApiOperation.Failure -> {
                        RequestState.Error(
                            error = ResponseMessage(
                                message = Res.string.user_info_not_found,
                                description = infoCall.error.message ?: ""
                            )
                        )
                    }
                    is ApiOperation.Success -> {
                        if (infoCall.value.data == null) {
                            return RequestState.Error(
                                error = ResponseMessage(
                                    message = Res.string.invalid_state,
                                    description = infoCall.value.message ?: ""
                                )
                            )
                        }

                        val newSession = saveSession(
                            id = id,
                            session = session.copy(user = call.value.data?.dtoToDomain()),
                            picker = infoCall.value.data!!.toPicker()
                        )
                            ?: return RequestState.Error(
                                error = ResponseMessage(
                                    message = Res.string.unexpected_error,
                                    description = "unable to save session"
                                )
                            )

                        RequestState.Success(
                            data = newSession
                        )
                    }
                }
            }
        }
    }

    private suspend fun saveSession(id: String, session: Session, picker: Picker): Session? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    if (session.user == null) {
                        rollback(null)
                    }
                    db.clossUserQueries.delete(id)

                    db.clossUserQueries.insert(
                        closs_user = session.user!!.domainToDb()
                    )

                    db.clossSessionQueries.insert(
                        closs_session = session.copy(active = true).sessionToDb()
                    )

                    db.clossPickerQueries.insert(
                        closs_picker = picker.toDbPicker(session.user!!.id)
                    )

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

                db.clossUserQueries.delete(session.user_id)
            }
        }
    }
}
