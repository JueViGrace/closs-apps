package org.closs.order.presentation.events

sealed interface OrdersEvents {
    data object Refresh : OrdersEvents
    data class NavigateToDetails(val id: String) : OrdersEvents
}
