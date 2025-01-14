package org.closs.order.presentation.state

import org.closs.core.types.shared.order.Order

data class OrderDetailsState(
    val order: Order? = null,
)
