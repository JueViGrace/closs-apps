package org.closs.order.presentation.orders.state

import org.closs.core.types.order.Order

data class OrdersState(
    val orders: List<Order> = emptyList(),
    val isLoading: Boolean = false
)
