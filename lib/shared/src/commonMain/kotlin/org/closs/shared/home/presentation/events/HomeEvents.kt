package org.closs.shared.home.presentation.events

sealed interface HomeEvents {
    data object ToggleDialog : HomeEvents
    data object NavigateToPickingHistory : HomeEvents
    data object NavigateToPendingOrders : HomeEvents
    data object NavigateToProfile : HomeEvents
    data object NavigateToNotifications : HomeEvents
    data object NavigateToSettings : HomeEvents
    data object Sync : HomeEvents
    data object LogOut : HomeEvents
}
