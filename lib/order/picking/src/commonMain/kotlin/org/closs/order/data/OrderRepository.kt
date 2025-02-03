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
import org.closs.core.types.order.findOrderToOrder
import org.closs.core.types.order.findOrdersToOrder
import org.closs.core.types.order.toClossOrder
import org.closs.core.types.order.toClossOrderLine
import org.closs.core.types.shared.product.toDbProduct
import org.closs.core.types.shared.state.RequestState
import kotlin.coroutines.CoroutineContext

interface OrderRepository {
    fun getOrders(): Flow<RequestState<List<Order>>>
    fun getOrder(orderId: String): Flow<RequestState<Order?>>
    fun fetchOrders(): Flow<RequestState<Boolean>>
    fun fetchOrder(orderId: String): Flow<RequestState<Boolean>>
}

class DefaultOrderRepository(
    private val client: OrderClient,
    private val dbHelper: PickingDbHelper,
    private val coroutineContext: CoroutineContext,
    private val scope: CoroutineScope,
    private val konnection: Konnection,
) : OrderRepository {
    override fun getOrders(): Flow<RequestState<List<Order>>> = flow {
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
            emit(
                RequestState.Success(
                    data = orders.findOrdersToOrder()
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

        dbHelper.withDatabase { db ->
            executeListAsFlow(
                query = db.clossOrderQueries.findOrder(session.user!!.id, orderId)
            )
        }.collect { rows ->
            emit(
                RequestState.Success(
                    data = rows.findOrderToOrder()
                )
            )
        }
    }.flowOn(coroutineContext)

    override fun fetchOrders(): Flow<RequestState<Boolean>> = flow {
        if (!konnection.isConnected()) {
            return@flow emit(
                RequestState.Error(
                    error = ""
                )
            )
        }

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

        when (val response = client.getOrders(session.accessToken)) {
            is ApiOperation.Failure -> {
                emit(
                    RequestState.Error(
                        error = response.error.message ?: ""
                    )
                )
            }
            is ApiOperation.Success -> {
                val orders = response.value.data
                    ?: return@flow emit(
                        RequestState.Error(
                            error = response.value.message ?: ""
                        )
                    )

                scope.async {
                    dbHelper.withDatabase { db ->
                        db.transaction {
                            db.clossOrderQueries.deleteByUser(session.user!!.id)
                            db.clossOrderLineQueries.deleteByUser(session.user!!.id)
                            db.clossProductQueries.deleteByUser(session.user!!.id)

                            orders.forEach { order ->
                                db.clossOrderQueries.insert(
                                    closs_order = order.toClossOrder(session.user!!.id)
                                )
                                order.lines.forEach { line ->
                                    db.clossOrderLineQueries.insert(
                                        closs_order_line = line.toClossOrderLine(session.user!!.id)
                                    )
                                    db.clossProductQueries.insert(
                                        closs_product = line.productDto.toDbProduct(session.user!!.id)
                                    )
                                }
                            }
                        }
                    }
                }.await()
                emit(
                    RequestState.Success(data = true)
                )
            }
        }
    }

    override fun fetchOrder(orderId: String): Flow<RequestState<Boolean>> = flow {
        if (!konnection.isConnected()) {
            return@flow emit(
                RequestState.Error(
                    error = ""
                )
            )
        }

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

        when (val response = client.getOrder(session.accessToken, orderId)) {
            is ApiOperation.Failure -> {
                emit(
                    RequestState.Error(
                        error = response.error.message ?: ""
                    )
                )
            }
            is ApiOperation.Success -> {
                val order = response.value.data
                    ?: return@flow emit(
                        RequestState.Error(
                            error = response.value.message ?: ""
                        )
                    )

                // todo: refactor insertion in the database
                scope.async {
                    dbHelper.withDatabase { db ->
                        db.transaction {
                            db.clossOrderQueries.deleteOne(session.user!!.id, orderId)

                            db.clossOrderQueries.insert(
                                closs_order = order.toClossOrder(session.user!!.id)
                            )
                            order.lines.forEach { line ->
                                db.clossOrderLineQueries.deleteOne(
                                    id = session.user!!.id,
                                    doc = line.documento,
                                    cod = line.productDto.codigo
                                )
                                db.clossProductQueries.deleteByOne(session.user!!.id, line.productDto.codigo)

                                db.clossOrderLineQueries.insert(
                                    closs_order_line = line.toClossOrderLine(session.user!!.id)
                                )
                                db.clossProductQueries.insert(
                                    closs_product = line.productDto.toDbProduct(session.user!!.id)
                                )
                            }
                        }
                    }
                }.await()
                emit(
                    RequestState.Success(data = true)
                )
            }
        }
    }
}
