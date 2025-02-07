package org.closs.order.presentation.orders.events

sealed interface OrdersEvents {
    data object Refresh : OrdersEvents
    data class NavigateToDetails(val id: String) : OrdersEvents
}
