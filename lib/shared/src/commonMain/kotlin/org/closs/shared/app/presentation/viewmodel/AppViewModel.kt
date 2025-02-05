package org.closs.shared.app.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.closs.core.presentation.shared.messages.Messages
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.shared.app.data.AppRepository
import org.closs.shared.app.presentation.state.AppState

abstract class AppViewModel(
    open val navigator: Navigator,
    open val messages: Messages,
    open val handle: SavedStateHandle,
    protected open val appRepository: AppRepository,
) : ViewModel() {
    open val state: StateFlow<AppState> = MutableStateFlow(AppState()).asStateFlow()
}
