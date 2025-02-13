package org.closs.order.presentation.history.state

import org.closs.core.types.order.Order

data class HistoryState(
    val isLoading: Boolean = false,
    val orders: List<Order> = emptyList(),
)
