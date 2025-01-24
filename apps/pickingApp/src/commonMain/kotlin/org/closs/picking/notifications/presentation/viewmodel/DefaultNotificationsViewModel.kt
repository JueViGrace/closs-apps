package org.closs.picking.notifications.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.shared.notifications.data.NotificationsRepository
import org.closs.shared.notifications.presentation.events.NotificationsEvents
import org.closs.shared.notifications.presentation.state.NotificationsState
import org.closs.shared.notifications.presentation.viewmodel.NotificationsViewModel

class DefaultNotificationsViewModel(
    override val repository: NotificationsRepository,
    override val navigator: Navigator,
    override val handle: SavedStateHandle
) : NotificationsViewModel(
    repository = repository,
    navigator = navigator,
    handle = handle
) {
    private val _state: MutableStateFlow<NotificationsState> = MutableStateFlow(NotificationsState())
    override val state: StateFlow<NotificationsState> = _state.asStateFlow()

    override fun onEvent(event: NotificationsEvents) {
    }
}
