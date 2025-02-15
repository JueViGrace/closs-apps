package org.closs.order.presentation.detail.state

import org.closs.core.types.order.Order

data class OrderDetailsState(
    val order: Order? = null,
    val isLoading: Boolean = false,
    val showFABMenu: Boolean = false,
)
