package org.closs.order.presentation.state

import org.closs.core.types.order.Order

data class OrdersListState(
    val orders: List<Order> = emptyList(),
)
