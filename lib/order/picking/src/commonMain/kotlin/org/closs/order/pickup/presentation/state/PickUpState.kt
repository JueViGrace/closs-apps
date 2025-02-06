package org.closs.order.pickup.presentation.state

import org.closs.core.types.order.Order

data class PickUpState(
    val order: Order? = null,
    val loading: Boolean = false,
    val showFilters: Boolean = false,
    val showFAB: Boolean = false,
)
