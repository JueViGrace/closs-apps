package org.closs.picking.home.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.closs.core.database.helper.PickingDbHelper
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.invalid_state
import org.closs.core.types.auth.dbActiveToDomain
import org.closs.core.types.shared.auth.Session
import org.closs.core.types.shared.state.RequestState
import org.closs.core.types.shared.state.ResponseMessage
import org.closs.shared.home.data.HomeRepository
import kotlin.coroutines.CoroutineContext

class DefaultHomeRepository(
    private val dbHelper: PickingDbHelper,
    override val coroutineContext: CoroutineContext,
    override val scope: CoroutineScope,
) : HomeRepository {
    override fun getSession(): Flow<RequestState<Session>> = flow {
        emit(RequestState.Loading)

        dbHelper.withDatabase { db ->
            executeOneAsFlow(
                query = db.sessionQueries.findActiveAccount()
            )
        }.catch { e ->
            emit(
                RequestState.Error(
                    error = ResponseMessage(
                        description = e.message ?: ""
                    )
                )
            )
        }.collect { session ->
            if (session == null) {
                return@collect emit(
                    RequestState.Error(
                        error = ResponseMessage(
                            message = Res.string.invalid_state,
                        )
                    )
                )
            }
            emit(
                RequestState.Success(
                    data = session.dbActiveToDomain()
                )
            )
        }
    }.flowOn(coroutineContext)

    override fun getOrdersCount(): Flow<Int> = flow {
        emit(0)
        val session = dbHelper.withDatabase { db ->
            executeOne(
                query = db.sessionQueries.findActiveAccount()
            )
        } ?: return@flow emit(0)

        dbHelper.withDatabase { db ->
            executeOneAsFlow(
                db.clossOrderQueries.countOrders(session.user_id)
            )
        }.collect { count ->
            emit(count?.toInt() ?: 0)
        }
    }.flowOn(coroutineContext)

    override fun sync(): Flow<RequestState<Boolean>> = flow {
        emit(RequestState.Loading)

        val session = dbHelper.withDatabase { db ->
            executeOne(
                query = db.sessionQueries.findActiveAccount()
            )
        } ?: return@flow emit(
            RequestState.Error(
                error = ResponseMessage(
                    message = Res.string.invalid_state
                )
            )
        )

        // todo: sync data
    }

    override suspend fun logOut() {
        scope.async {
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
        }.await()
    }
}
