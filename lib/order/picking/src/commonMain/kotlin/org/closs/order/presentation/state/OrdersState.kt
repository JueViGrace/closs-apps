package org.closs.order.presentation.state

import org.closs.core.types.order.Order
import org.closs.core.types.shared.state.RequestState

data class OrdersState(
    val orders: RequestState<List<Order>> = RequestState.Idle,
)
