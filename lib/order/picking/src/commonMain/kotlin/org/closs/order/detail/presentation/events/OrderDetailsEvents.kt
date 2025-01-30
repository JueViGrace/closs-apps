package org.closs.order.detail.presentation.events

sealed interface OrderDetailsEvents {
    data object OnEditOrder : OrderDetailsEvents
}
