package org.closs.order.presentation.pickup.state

import org.closs.core.types.order.Order
import org.jetbrains.compose.resources.StringResource

data class PickUpState(
    val dbOrder: Order? = null,
    val order: Order? = null,
    val isLoading: Boolean = false,
    val updateLoading: Boolean = false,

    val cartId: String = "",
    val showCartDialog: Boolean = true,
    val cartIdError: StringResource? = null,
    val submitCartEnabled: Boolean = false,

    val showFilters: Boolean = false,

    val showFAB: Boolean = false,

    val showCancelDialog: Boolean = false,
)
