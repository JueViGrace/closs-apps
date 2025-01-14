package org.closs.order.presentation.state

import org.closs.core.types.shared.order.Order

data class OrdersListState(
    val orders: List<Order> = emptyList(),
)
