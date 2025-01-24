package org.closs.shared.notifications.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.shared.notifications.data.NotificationsRepository
import org.closs.shared.settings.presentation.events.SettingsEvents
import org.closs.shared.settings.presentation.state.SettingsState

abstract class NotificationsViewModel(
    protected open val repository: NotificationsRepository,
    protected open val navigator: Navigator,
    protected open val handle: SavedStateHandle
) : ViewModel() {
    open val state: StateFlow<SettingsState> = MutableStateFlow(SettingsState()).asStateFlow()

    abstract fun onEvent(event: SettingsEvents)
}
