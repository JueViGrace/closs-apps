package org.closs.order.pickup.presentation.events

sealed interface PickUpEvents {
    data object ToggleFilters : PickUpEvents
    data object ToggleCancelDialog : PickUpEvents
    data object OnCancelPickUp : PickUpEvents
    data class OnQuantityChange(val index: Int, val quantity: Int) : PickUpEvents
    data class ToggleCheckProduct(val index: Int) : PickUpEvents
    data object OnSubmitPickUp : PickUpEvents
}
