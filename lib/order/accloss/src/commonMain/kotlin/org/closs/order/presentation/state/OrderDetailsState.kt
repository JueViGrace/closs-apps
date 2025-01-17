package org.closs.order.presentation.state

import org.closs.core.types.order.Order

data class OrderDetailsState(
    val order: Order? = null,
)
