package org.closs.shared.home.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.shared.home.data.HomeRepository
import org.closs.shared.home.presentation.events.HomeEvents
import org.closs.shared.home.presentation.state.HomeState

abstract class HomeViewModel(
    protected open val repository: HomeRepository,
    protected open val navigator: Navigator,
    protected open val handle: SavedStateHandle
) : ViewModel() {
    open val state: StateFlow<HomeState> = MutableStateFlow(HomeState()).asStateFlow()

    abstract fun onEvent(event: HomeEvents)

    abstract fun toggleDialog()

    protected open fun navigateToProfile() {
        toggleDialog()
        viewModelScope.launch {
            delay(100)
            navigator.navigate(
                destination = Destination.Profile,
                navOptions = NavOptions.Builder().apply {
                    setPopUpTo(Destination.Home, false)
                    setLaunchSingleTop(true)
                }.build()
            )
        }
    }

    protected fun navigateToNotifications() {
        toggleDialog()
        viewModelScope.launch {
            delay(100)
            navigator.navigate(
                destination = Destination.Notifications,
                navOptions = NavOptions.Builder().apply {
                    setPopUpTo(Destination.Home, false)
                    setLaunchSingleTop(true)
                }.build()
            )
        }
    }

    protected fun navigateToSettings() {
        toggleDialog()
        viewModelScope.launch {
            delay(100)
            navigator.navigate(
                destination = Destination.Settings,
                navOptions = NavOptions.Builder().apply {
                    setPopUpTo(Destination.Home, false)
                    setLaunchSingleTop(true)
                }.build()
            )
        }
    }

    abstract fun sync()

    abstract fun logOut()
}
