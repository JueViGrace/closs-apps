package org.closs.picking.home.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
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
import org.closs.core.types.shared.state.RequestState
import org.closs.shared.home.data.HomeRepository
import org.closs.shared.home.presentation.events.HomeEvents
import org.closs.shared.home.presentation.state.HomeState
import org.closs.shared.home.presentation.viewmodel.HomeViewModel

class DefaultHomeViewModel(
    override val repository: HomeRepository,
    override val navigator: Navigator,
    override val handle: SavedStateHandle
) : HomeViewModel(
    repository = repository,
    navigator = navigator,
    handle = handle
) {
    private val _session = repository.getSession()
    private val _orderCount = repository.getOrdersCount()

    private val _state = MutableStateFlow(HomeState())
    override val state = combine(
        _state,
        _session,
        _orderCount,
    ) { state, session, orderCount ->
        when (session) {
            is RequestState.Error -> {
                state.copy(
                    orderCount = orderCount,
                    session = null,
                )
            }
            is RequestState.Success -> {
                state.copy(
                    orderCount = orderCount,
                    session = session.data,
                )
            }
            else -> {
                state.copy(
                    orderCount = orderCount,
                    session = null,
                )
            }
        }
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
            HomeEvents.LogOut -> logOut()
            else -> {}
        }
    }

    override fun toggleDialog() {
        _state.update { state ->
            state.copy(
                showDialog = !state.showDialog
            )
        }
    }

    private fun navigateToPickingHistory() {
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.PickingHistory,
                navOptions = NavOptions.Builder().apply {
                    setPopUpTo(Destination.Home, false)
                    setLaunchSingleTop(true)
                }.build()
            )
        }
    }

    private fun navigateToPendingOrders() {
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.Orders,
                navOptions = NavOptions.Builder().apply {
                    setPopUpTo(Destination.Home, false)
                    setLaunchSingleTop(true)
                }.build()
            )
        }
    }

    override fun sync() {
    }

    override fun logOut() {
        toggleDialog()
        _state.update { state ->
            state.copy(
                session = null,
                isSyncing = false,
                showDialog = false
            )
        }
        viewModelScope.launch {
            delay(100)
            repository.logOut()
            navigator.navigate(
                destination = Destination.SignIn,
                navOptions = NavOptions.Builder().apply {
                    setPopUpTo(Destination.Home, true)
                }.build()
            )
        }
    }
}
