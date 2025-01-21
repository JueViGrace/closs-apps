package org.closs.app.shared.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.closs.app.shared.data.AppRepository
import org.closs.app.shared.presentation.state.AppState
import org.closs.core.presentation.shared.messages.Messages
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.Navigator

// Refactor session queries
abstract class AppViewModel(
    open val navigator: Navigator,
    open val messages: Messages,
    open val handle: SavedStateHandle,
    protected open val appRepository: AppRepository,
) : ViewModel() {
    open val state: StateFlow<AppState> = MutableStateFlow(AppState()).asStateFlow()

    abstract fun toggleDialog()

    open fun navigateToNotifications() {
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.Notifications,
                navOptions = NavOptions.Builder().apply {
                    setPopUpTo(Destination.Home, false)
                }.build()
            )
        }
    }
}
