package org.closs.app.shared.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.closs.app.shared.data.AppRepository
import org.closs.app.shared.presentation.state.AppState
import org.closs.core.presentation.shared.messages.Messages
import org.closs.core.presentation.shared.navigation.Navigator

abstract class AppViewModel(
    open val navigator: Navigator,
    open val messages: Messages,
    protected open val appRepository: AppRepository,
) : ViewModel() {
    protected open val _state: MutableStateFlow<AppState> = MutableStateFlow(AppState())
    open val state: StateFlow<AppState> = MutableStateFlow(AppState()).asStateFlow()
}
