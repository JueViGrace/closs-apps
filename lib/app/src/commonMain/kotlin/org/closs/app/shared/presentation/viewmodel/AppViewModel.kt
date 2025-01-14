package org.closs.app.shared.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import org.closs.app.shared.data.AppRepository
import org.closs.app.shared.presentation.state.AppState
import org.closs.core.presentation.shared.messages.Messages
import org.closs.core.presentation.shared.navigation.Navigator

abstract class AppViewModel(
    open val navigator: Navigator,
    open val messages: Messages,
    open val appRepository: AppRepository,
) : ViewModel() {
    open val state: StateFlow<AppState> = flowOf(AppState()).stateIn(viewModelScope, SharingStarted.Lazily, AppState())
}
