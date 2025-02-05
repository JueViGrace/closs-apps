package org.closs.picking.app.data

import dev.tmapps.konnection.Konnection
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
import org.closs.core.types.auth.dbActiveToDomain
import org.closs.core.types.picker.Picker
import org.closs.core.types.picker.toDbPicker
import org.closs.core.types.picker.toPicker
import org.closs.core.types.shared.auth.Session
import org.closs.core.types.shared.auth.dtoToDomain
import org.closs.core.types.shared.auth.sessionToDb
import org.closs.core.types.shared.state.AppCodes
import org.closs.core.types.shared.state.RequestState
import org.closs.core.types.shared.user.domainToDb
import org.closs.core.types.shared.user.dtoToDomain
import org.closs.shared.app.data.AppRepository
import kotlin.coroutines.CoroutineContext

class DefaultAppRepository(
    private val authClient: AuthClient,
    private val userClient: UserClient,
    private val dbHelper: PickingDbHelper,
    private val konnection: Konnection,
    override val coroutineContext: CoroutineContext,
    override val scope: CoroutineScope,
) : AppRepository {
    override fun validateSession(): Flow<RequestState<Session>> {
        return flow {
            emit(RequestState.Loading)

            val session = dbHelper.withDatabase { db ->
                executeOne(
                    query = db.sessionQueries.findActiveAccount()
                )
            }
            if (session == null) {
                return@flow emit(
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
            } else {
                return@flow emit(
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

    private suspend fun refresh(session: Session): RequestState<Session> {
        return when (val refreshCall = authClient.refresh(refreshToken = session.refreshToken)) {
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
                    id = session.user!!.id,
                    session = refreshCall.value.data!!.dtoToDomain(),
                )
            }
        }
    }

    // refactor this sht
    private suspend fun getUserData(id: String, session: Session): RequestState<Session> {
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
                            id = id,
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

    private suspend fun saveSession(id: String, session: Session, picker: Picker): Session? {
        return scope.async {
            dbHelper.withDatabase { db ->
                db.transactionWithResult {
                    if (session.user == null) {
                        rollback(null)
                    }
                    db.clossSessionQueries.deleteSession(id)

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
                db.clossOrderQueries.deleteByUser(session.user_id)
                db.clossOrderLineQueries.deleteByUser(session.user_id)
            }
        }
    }
}
