package org.closs.order.data

import dev.tmapps.konnection.Konnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.closs.core.api.order.OrderClient
import org.closs.core.api.shared.client.ApiOperation
import org.closs.core.database.helper.PickingDbHelper
import org.closs.core.types.auth.dbActiveToDomain
import org.closs.core.types.order.Order
import org.closs.core.types.order.toClossOrder
import org.closs.core.types.order.toClossOrderLine
import org.closs.core.types.order.toOrder
import org.closs.core.types.order.toOrderWithLines
import org.closs.core.types.shared.auth.Session
import org.closs.core.types.shared.state.RequestState
import kotlin.coroutines.CoroutineContext

interface OrderRepository {
    fun getOrders(reload: Boolean = false): Flow<RequestState<List<Order>>>
    fun getOrder(orderId: String): Flow<RequestState<Order?>>
}

class DefaultOrderRepository(
    private val client: OrderClient,
    private val dbHelper: PickingDbHelper,
    private val coroutineContext: CoroutineContext,
    private val scope: CoroutineScope,
    private val konnection: Konnection,
) : OrderRepository {
    override fun getOrders(reload: Boolean): Flow<RequestState<List<Order>>> = flow {
        var forceReload = reload
        emit(RequestState.Loading)
        val session = dbHelper.withDatabase { db ->
            executeOne(db.sessionQueries.findActiveAccount())?.dbActiveToDomain()
        } ?: return@flow emit(
            RequestState.Error(
                error = ""
            )
        )
        if (session.user == null) {
            return@flow emit(
                RequestState.Error(
                    error = "invalid state"
                )
            )
        }

        dbHelper.withDatabase { db ->
            executeListAsFlow(
                query = db.clossOrderQueries.findOrders(session.user!!.id)
            )
        }.collect { orders ->
            if ((orders.isEmpty() || forceReload) && konnection.isConnected()) {
                when (val fetch = fetchOrders(session)) {
                    is RequestState.Error -> {
                        emit(
                            RequestState.Error(
                                error = fetch.error
                            )
                        )
                    }
                    is RequestState.Success -> {
                        forceReload = false
                    }
                    else -> {
                        emit(RequestState.Loading)
                    }
                }
            }
            emit(
                RequestState.Success(
                    data = orders.map { order -> order.toOrder() }
                )
            )
        }
    }.flowOn(coroutineContext)

    override fun getOrder(orderId: String): Flow<RequestState<Order?>> = flow {
        emit(RequestState.Loading)
        val session = dbHelper.withDatabase { db ->
            executeOne(db.sessionQueries.findActiveAccount())?.dbActiveToDomain()
        } ?: return@flow emit(
            RequestState.Error(
                error = ""
            )
        )
        if (session.user == null) {
            return@flow emit(
                RequestState.Error(
                    error = "invalid state"
                )
            )
        }

        when (val fetch = fetchOrder(session, orderId)) {
            is RequestState.Error -> {
                emit(
                    RequestState.Error(
                        error = fetch.error
                    )
                )
            }
            is RequestState.Success -> {
                dbHelper.withDatabase { db ->
                    executeListAsFlow(
                        query = db.clossOrderQueries.findOrder(session.user!!.id, orderId)
                    )
                }.collect { rows ->
                    emit(
                        RequestState.Success(
                            data = rows.toOrderWithLines()
                        )
                    )
                }
            }
            else -> {
                emit(RequestState.Loading)
            }
        }
    }.flowOn(coroutineContext)

    private suspend fun fetchOrders(session: Session): RequestState<Boolean> {
        return when (val response = client.getOrders(session.accessToken)) {
            is ApiOperation.Failure -> {
                return RequestState.Error(
                    error = response.error.message ?: ""
                )
            }
            is ApiOperation.Success -> {
                val orders = response.value.data
                    ?: return RequestState.Error(
                        error = response.value.message ?: ""
                    )

                scope.async {
                    dbHelper.withDatabase { db ->
                        db.transaction {
                            db.clossOrderQueries.deleteByUser(session.user!!.id)
                            db.clossOrderLineQueries.deleteByUser(session.user!!.id)

                            orders.forEach { order ->
                                db.clossOrderQueries.insert(
                                    closs_order = order.toClossOrder(session.user!!.id)
                                )
                                order.lines.forEach { line ->
                                    db.clossOrderLineQueries.insert(
                                        closs_order_line = line.toClossOrderLine(session.user!!.id)
                                    )
                                }
                            }
                        }
                    }
                }.await()
                RequestState.Success(data = true)
            }
        }
    }

    private suspend fun fetchOrder(session: Session, orderId: String): RequestState<Boolean> {
        return when (val response = client.getOrder(session.accessToken, orderId)) {
            is ApiOperation.Failure -> {
                return RequestState.Error(
                    error = response.error.message ?: ""
                )
            }
            is ApiOperation.Success -> {
                val order = response.value.data
                    ?: return RequestState.Error(
                        error = response.value.message ?: ""
                    )

                scope.async {
                    dbHelper.withDatabase { db ->
                        db.transaction {
                            db.clossOrderQueries.deleteByUser(session.user!!.id)
                            db.clossOrderLineQueries.deleteByUser(session.user!!.id)

                            db.clossOrderQueries.insert(
                                closs_order = order.toClossOrder(session.user!!.id)
                            )
                            order.lines.forEach { line ->
                                db.clossOrderLineQueries.insert(
                                    closs_order_line = line.toClossOrderLine(session.user!!.id)
                                )
                            }
                        }
                    }
                }.await()
                RequestState.Success(data = true)
            }
        }
    }
}
