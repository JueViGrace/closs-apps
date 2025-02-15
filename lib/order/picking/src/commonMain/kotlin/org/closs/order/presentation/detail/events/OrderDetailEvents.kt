package org.closs.order.presentation.detail.events

import org.closs.core.presentation.shared.actions.FABActions

sealed interface OrderDetailEvents {
    data object Refresh : OrderDetailEvents
    data object ToggleFABMenu : OrderDetailEvents
    data class OnFABActionClick(val action: FABActions) : OrderDetailEvents
}
