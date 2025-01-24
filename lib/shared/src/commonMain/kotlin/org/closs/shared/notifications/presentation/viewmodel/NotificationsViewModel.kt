package org.closs.shared.notifications.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.shared.notifications.data.NotificationsRepository
import org.closs.shared.notifications.presentation.events.NotificationsEvents
import org.closs.shared.notifications.presentation.state.NotificationsState

abstract class NotificationsViewModel(
    protected open val repository: NotificationsRepository,
    open val navigator: Navigator,
    protected open val handle: SavedStateHandle
) : ViewModel() {
    open val state: StateFlow<NotificationsState> = MutableStateFlow(NotificationsState()).asStateFlow()

    abstract fun onEvent(event: NotificationsEvents)
}
