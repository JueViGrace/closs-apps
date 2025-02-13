package org.closs.order.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.closs.core.api.order.OrderClient
import org.closs.core.api.shared.client.ApiOperation
import org.closs.core.database.helper.PickingDbHelper
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.invalid_state
import org.closs.core.resources.resources.generated.resources.order_not_found
import org.closs.core.resources.resources.generated.resources.orders_not_found
import org.closs.core.resources.resources.generated.resources.token_in_use
import org.closs.core.types.auth.dbActiveToDomain
import org.closs.core.types.order.Order
import org.closs.core.types.order.dto.OrderDto
import org.closs.core.types.order.mappers.findHistoryOrdersToOrder
import org.closs.core.types.order.mappers.findOrderToOrder
import org.closs.core.types.order.mappers.findOrdersToOrder
import org.closs.core.types.order.mappers.toDbOrder
import org.closs.core.types.order.mappers.toDbOrderLine
import org.closs.core.types.order.mappers.toUpdateCartDto
import org.closs.core.types.order.mappers.toUpdateDto
import org.closs.core.types.shared.auth.Session
import org.closs.core.types.shared.product.mappers.toDbProduct
import org.closs.core.types.shared.state.RequestState
import org.closs.core.types.shared.state.ResponseMessage
import kotlin.coroutines.CoroutineContext

interface OrderRepository {
    fun getPendingOrders(): Flow<RequestState<List<Order>>>
    fun getHistoryOrders(): Flow<RequestState<List<Order>>>
    fun getOrder(orderId: String): Flow<RequestState<Order>>
    fun fetchOrders(): Flow<RequestState<Boolean>>
    fun fetchOrder(orderId: String): Flow<RequestState<Boolean>>
    fun submitCartId(order: Order): Flow<RequestState<String>>
    fun submitOrder(order: Order): Flow<RequestState<String>>
    suspend fun deleteOrder(orderId: String, userId: String)
}

class DefaultOrderRepository(
    private val client: OrderClient,
    private val dbHelper: PickingDbHelper,
    private val coroutineContext: CoroutineContext,
    private val scope: CoroutineScope,
) : OrderRepository {

    // todo: websocket connection
    override fun getPendingOrders(): Flow<RequestState<List<Order>>> = flow {
        emit(RequestState.Loading)
        val session = dbHelper.withDatabase { db ->
            executeOne(db.sessionQueries.findActiveAccount())?.dbActiveToDomain()
        } ?: return@flow emit(
            RequestState.Error(
                error = ResponseMessage(
                    message = Res.string.invalid_state,
                )
            )
        )
        if (session.user == null) {
            return@flow emit(
                RequestState.Error(
                    error = ResponseMessage(
                        message = Res.string.invalid_state,
                    )
                )
            )
        }

        dbHelper.withDatabase { db ->
            executeListAsFlow(
                query = db.clossOrderQueries.findPendingOrders(session.user!!.id)
            )
        }.collect { orders ->
            emit(
                RequestState.Success(
                    data = orders.findOrdersToOrder()
                )
            )
        }
    }.flowOn(coroutineContext)

    override fun getHistoryOrders(): Flow<RequestState<List<Order>>> = flow {
        emit(RequestState.Loading)
        val session = dbHelper.withDatabase { db ->
            executeOne(db.sessionQueries.findActiveAccount())?.dbActiveToDomain()
        } ?: return@flow emit(
            RequestState.Error(
                error = ResponseMessage(
                    message = Res.string.invalid_state,
                )
            )
        )
        if (session.user == null) {
            return@flow emit(
                RequestState.Error(
                    error = ResponseMessage(
                        message = Res.string.invalid_state,
                    )
                )
            )
        }

        dbHelper.withDatabase { db ->
            executeListAsFlow(
                query = db.clossOrderQueries.findHistoryOrders(session.user!!.id)
            )
        }.collect { orders ->
            emit(
                RequestState.Success(
                    data = orders.findHistoryOrdersToOrder()
                )
            )
        }
    }.flowOn(coroutineContext)

    override fun getOrder(orderId: String): Flow<RequestState<Order>> = flow {
        emit(RequestState.Loading)
        val session = dbHelper.withDatabase { db ->
            executeOne(db.sessionQueries.findActiveAccount())?.dbActiveToDomain()
        } ?: return@flow emit(
            RequestState.Error(
                error = ResponseMessage(
                    message = Res.string.invalid_state,
                )
            )
        )
        if (session.user == null) {
            return@flow emit(
                RequestState.Error(
                    error = ResponseMessage(
                        message = Res.string.invalid_state,
                    )
                )
            )
        }

        dbHelper.withDatabase { db ->
            executeListAsFlow(
                query = db.clossOrderQueries.findOrder(session.user!!.id, orderId)
            )
        }.collect { rows ->
            val order = rows.findOrderToOrder()
                ?: return@collect emit(
                    RequestState.Error(
                        error = ResponseMessage(
                            message = Res.string.order_not_found
                        )
                    )
                )

            emit(
                RequestState.Success(
                    data = order
                )
            )
        }
    }.flowOn(coroutineContext)

    override fun fetchOrders(): Flow<RequestState<Boolean>> = flow {
        val session = dbHelper.withDatabase { db ->
            executeOne(db.sessionQueries.findActiveAccount())?.dbActiveToDomain()
        } ?: return@flow emit(
            RequestState.Error(
                error = ResponseMessage(
                    message = Res.string.invalid_state
                )
            )
        )
        if (session.user == null) {
            return@flow emit(
                RequestState.Error(
                    error = ResponseMessage(
                        message = Res.string.invalid_state
                    )
                )
            )
        }

        when (val response = client.getOrders(session.accessToken)) {
            is ApiOperation.Failure -> {
                emit(
                    RequestState.Error(
                        error = ResponseMessage(
                            message = Res.string.orders_not_found,
                            description = response.error.message ?: ""
                        )
                    )
                )
            }
            is ApiOperation.Success -> {
                val orders = response.value.data
                    ?: return@flow emit(
                        RequestState.Error(
                            error = ResponseMessage(
                                message = Res.string.orders_not_found,
                                description = response.value.message ?: ""
                            )
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
                                    closs_order = order.toDbOrder(session.user!!.id)
                                )
                                order.lines.forEach { line ->
                                    db.clossOrderLineQueries.insert(
                                        closs_order_line = line.toDbOrderLine(session.user!!.id)
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
        val session = dbHelper.withDatabase { db ->
            executeOne(db.sessionQueries.findActiveAccount())?.dbActiveToDomain()
        } ?: return@flow emit(
            RequestState.Error(
                error = ResponseMessage(
                    message = Res.string.invalid_state,
                )
            )
        )
        if (session.user == null) {
            return@flow emit(
                RequestState.Error(
                    error = ResponseMessage(
                        message = Res.string.invalid_state,
                    )
                )
            )
        }

        when (val response = client.getOrder(session.accessToken, orderId)) {
            is ApiOperation.Failure -> {
                emit(
                    RequestState.Error(
                        error = ResponseMessage(
                            message = Res.string.order_not_found,
                            description = response.error.message ?: ""
                        )
                    )
                )
            }
            is ApiOperation.Success -> {
                val order = response.value.data
                    ?: return@flow emit(
                        RequestState.Error(
                            error = ResponseMessage(
                                message = Res.string.order_not_found,
                                description = response.value.message ?: ""
                            )
                        )
                    )

                saveOrder(session, order)
                emit(
                    RequestState.Success(data = true)
                )
            }
        }
    }

    override fun submitCartId(order: Order): Flow<RequestState<String>> = flow {
        emit(RequestState.Loading)

        val session = dbHelper.withDatabase { db ->
            executeOne(db.sessionQueries.findActiveAccount())
        }?.dbActiveToDomain()
            ?: return@flow emit(
                RequestState.Error(
                    error = ResponseMessage(
                        message = Res.string.invalid_state
                    )
                )
            )

        when (val call = client.updateCartId(session.accessToken, order.toUpdateCartDto())) {
            is ApiOperation.Failure -> {
                when (call.error.status) {
                    409 -> {
                        emit(
                            RequestState.Error(
                                error = ResponseMessage(
                                    message = Res.string.token_in_use
                                )
                            )
                        )
                    }
                    else -> {
                        emit(
                            RequestState.Error(
                                error = ResponseMessage(
                                    message = Res.string.order_not_found,
                                    description = call.error.message ?: ""
                                )
                            )
                        )
                    }
                }
            }
            is ApiOperation.Success -> {
                val data = call.value.data
                    ?: return@flow emit(
                        RequestState.Error(
                            error = ResponseMessage(
                                message = Res.string.order_not_found,
                                description = call.value.message ?: ""
                            )
                        )
                    )

                saveOrder(session, data)

                emit(
                    RequestState.Success(
                        data = ""
                    )
                )
            }
        }
    }

    override fun submitOrder(order: Order): Flow<RequestState<String>> = flow {
        emit(RequestState.Loading)

        val session = dbHelper.withDatabase { db ->
            executeOne(db.sessionQueries.findActiveAccount())
        }?.dbActiveToDomain()
            ?: return@flow emit(
                RequestState.Error(
                    error = ResponseMessage(
                        message = Res.string.invalid_state
                    )
                )
            )

        when (val call = client.updateOrder(session.accessToken, order.toUpdateDto())) {
            is ApiOperation.Failure -> {
                emit(
                    RequestState.Error(
                        error = ResponseMessage(
                            message = Res.string.order_not_found,
                            description = call.error.message ?: ""
                        )
                    )
                )
            }
            is ApiOperation.Success -> {
                val data = call.value.data
                    ?: return@flow emit(
                        RequestState.Error(
                            error = ResponseMessage(
                                message = Res.string.order_not_found,
                                description = call.value.message ?: ""
                            )
                        )
                    )

                saveOrder(session, data)

                emit(
                    RequestState.Success(
                        data = ""
                    )
                )
            }
        }
    }.flowOn(coroutineContext)

    override suspend fun deleteOrder(orderId: String, userId: String) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.transaction {
                    db.clossOrderQueries.deleteOne(orderId, userId)
                    db.clossOrderLineQueries.deleteByDoc(orderId, userId)
                }
            }
        }.await()
    }

    private suspend fun saveOrder(session: Session, dto: OrderDto) {
        scope.async {
            dbHelper.withDatabase { db ->
                db.transaction {
                    db.clossOrderQueries.deleteOne(session.user!!.id, dto.documento)

                    db.clossOrderQueries.insert(
                        closs_order = dto.toDbOrder(session.user!!.id)
                    )
                    dto.lines.forEach { line ->
                        db.clossOrderLineQueries.deleteOne(
                            id = session.user!!.id,
                            doc = line.documento,
                            cod = line.productDto.codigo
                        )
                        db.clossProductQueries.deleteByOne(session.user!!.id, line.productDto.codigo)

                        db.clossOrderLineQueries.insert(
                            closs_order_line = line.toDbOrderLine(session.user!!.id)
                        )
                        db.clossProductQueries.insert(
                            closs_product = line.productDto.toDbProduct(session.user!!.id)
                        )
                    }
                }
            }
        }.await()
    }
}
