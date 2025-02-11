package org.closs.order.presentation.pickup.state

import org.closs.core.types.order.Order
import org.jetbrains.compose.resources.StringResource

data class PickUpState(
    val dbOrder: Order? = null,
    val order: Order? = null,
    val isLoading: Boolean = false,
    val updateLoading: Boolean = false,

    val showCartDialog: Boolean = false,
    val cartLoading: Boolean = false,
    val cartIdValue: String = "",
    val cartIdError: StringResource? = null,
    val cartSubmitEnabled: Boolean = false,

    val quantityValue: String = "",
    val quantityError: StringResource? = null,

    val showFilters: Boolean = false,

    val showFAB: Boolean = false,

    val showCancelDialog: Boolean = false,
)
