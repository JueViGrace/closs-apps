package org.closs.core.api.order

import org.closs.core.api.shared.client.ApiOperation
import org.closs.core.api.shared.client.KtorClient
import org.closs.core.api.shared.client.call
import org.closs.core.api.shared.client.get
import org.closs.core.api.shared.client.put
import org.closs.core.types.order.dto.OrderDto

class OrderClient(
    private val client: KtorClient
) {
    suspend fun getOrders(token: String): ApiOperation<List<OrderDto>> {
        return client.call {
            get(
                urlString = "/api/orders",
                headers = mapOf("Authorization" to "Bearer $token")
            )
        }
    }

    suspend fun getOrder(token: String, orderId: String): ApiOperation<OrderDto?> {
        return client.call {
            get(
                urlString = "/api/orders/$orderId",
                headers = mapOf("Authorization" to "Bearer $token")
            )
        }
    }

    suspend fun updateOrder(token: String, order: OrderDto): ApiOperation<OrderDto?> {
        return client.call {
            put(
                urlString = "/api/orders",
                headers = mapOf("Authorization" to "Bearer $token"),
                body = order
            )
        }
    }
}
