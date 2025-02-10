package org.closs.core.api.order

import org.closs.core.api.shared.client.ApiOperation
import org.closs.core.api.shared.client.KtorClient
import org.closs.core.api.shared.client.call
import org.closs.core.api.shared.client.get
import org.closs.core.api.shared.client.patch
import org.closs.core.api.shared.client.put
import org.closs.core.types.order.dto.OrderDto
import org.closs.core.types.order.dto.UpdateOrderCartDto
import org.closs.core.types.order.dto.UpdateOrderDto

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

    suspend fun updateCartId(token: String, order: UpdateOrderCartDto): ApiOperation<OrderDto?> {
        return client.call {
            patch(
                urlString = "/api/orders",
                headers = mapOf("Authorization" to "Bearer $token"),
                body = order
            )
        }
    }

    suspend fun updateOrder(token: String, order: UpdateOrderDto): ApiOperation<OrderDto?> {
        return client.call {
            put(
                urlString = "/api/orders",
                headers = mapOf("Authorization" to "Bearer $token"),
                body = order
            )
        }
    }
}
