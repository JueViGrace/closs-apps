package org.closs.picking.home.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.types.shared.common.Constants
import org.closs.home.shared.presentation.events.HomeEvents
import org.closs.home.shared.presentation.viewmodel.HomeViewModel
import org.closs.picking.home.presentation.state.HomeState

class DefaultHomeViewModel(
    override val navigator: Navigator,
    override val handle: SavedStateHandle
) : HomeViewModel(
    navigator = navigator,
    handle = handle
) {
    private val _showDialog = handle.getStateFlow(Constants.SHOW_HOME_DIALOG_KEY, false)

    private val _state = MutableStateFlow(HomeState())
    val state = combine(
        _state,
        _showDialog
    ) { state, showDialog ->
        state.copy(
            showDialog = showDialog
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _state.value
    )

    override fun onEvent(event: HomeEvents) {
        when (event) {
            HomeEvents.ToggleDialog -> toggleDialog()
            HomeEvents.NavigateToPickingHistory -> navigateToPickingHistory()
            HomeEvents.NavigateToPendingOrders -> navigateToPendingOrders()
            HomeEvents.NavigateToProfile -> navigateToProfile()
            HomeEvents.NavigateToNotifications -> navigateToNotifications()
            HomeEvents.NavigateToSettings -> navigateToSettings()
            HomeEvents.Sync -> sync()
        }
    }

    override fun toggleDialog() {
        handle[Constants.SHOW_HOME_DIALOG_KEY] = !_showDialog.value
    }

    override fun sync() {

    }

    private fun navigateToPickingHistory() {
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.PickingHistory,
                navOptions = NavOptions.Builder().apply {
                    setPopUpTo(Destination.Home, false)
                }.build()
            )
        }
    }

    private fun navigateToPendingOrders() {
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.PendingOrders,
                navOptions = NavOptions.Builder().apply {
                    setPopUpTo(Destination.Home, false)
                }.build()
            )
        }
    }


}
