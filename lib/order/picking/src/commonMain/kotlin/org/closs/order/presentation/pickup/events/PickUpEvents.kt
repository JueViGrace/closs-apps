package org.closs.order.presentation.pickup.events

sealed interface PickUpEvents {
    data object Dismiss : PickUpEvents
    data class OnCartChange(val id: String) : PickUpEvents
    data object OnSubmitCartId : PickUpEvents
    data object ToggleFilters : PickUpEvents
    data object ToggleCancelDialog : PickUpEvents
    data object CancelPickUp : PickUpEvents
    data class OnQuantityChange(val index: Int, val quantity: String) : PickUpEvents
    data class ToggleCheckProduct(val index: Int, val checked: Boolean) : PickUpEvents
    data object OnSubmitPickUp : PickUpEvents
}
