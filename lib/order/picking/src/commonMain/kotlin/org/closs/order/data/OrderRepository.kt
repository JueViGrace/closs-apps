package org.closs.order.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.closs.core.api.order.OrderClient
import org.closs.core.api.shared.client.ApiOperation
import org.closs.core.database.helper.PickingDbHelper
import org.closs.core.types.auth.dbActiveToDomain
import org.closs.core.types.order.Order
import org.closs.core.types.shared.state.RequestState
import kotlin.coroutines.CoroutineContext

interface OrderRepository {
    suspend fun getOrders(): Flow<RequestState<List<Order>>>
    suspend fun getOrder(orderId: String): Flow<RequestState<Order?>>
}

class DefaultOrderRepository(
    private val client: OrderClient,
    private val dbHelper: PickingDbHelper,
    private val coroutineContext: CoroutineContext
) : OrderRepository {
    override suspend fun getOrders(): Flow<RequestState<List<Order>>> = flow {
        emit(RequestState.Loading)
        dbHelper.withDatabase { db ->
            val session = executeOne(db.sessionQueries.findActiveAccount())?.dbActiveToDomain()
                ?: return@withDatabase emit(
                    RequestState.Error(
                        error = ""
                    )
                )
            when (val response = client.getOrders(session.accessToken)) {
                is ApiOperation.Failure -> {
                    emit(
                        RequestState.Error(
                            error = response.error.message ?: ""
                        )
                    )
                }
                is ApiOperation.Success -> {
                    db.transaction {
                        // todo: create order table and queries
                    }
                }
            }
        }
    }.flowOn(coroutineContext)

    override suspend fun getOrder(orderId: String): Flow<RequestState<Order?>> = flow<RequestState<Order?>> {
        emit(RequestState.Loading)
        dbHelper.withDatabase { db ->
            val session = executeOne(db.sessionQueries.findActiveAccount())?.dbActiveToDomain()
                ?: return@withDatabase emit(
                    RequestState.Error(
                        error = ""
                    )
                )
            when (val response = client.getOrder(session.accessToken, orderId)) {
                is ApiOperation.Failure -> {
                    emit(
                        RequestState.Error(
                            error = response.error.message ?: ""
                        )
                    )
                }
                is ApiOperation.Success -> {
                    db.transaction {
                        // todo: create order table and queries
                    }
                }
            }
        }
    }.flowOn(coroutineContext)
}
