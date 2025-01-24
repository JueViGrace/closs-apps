package org.closs.picking.settings.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.shared.settings.data.SettingsRepository
import org.closs.shared.settings.presentation.events.SettingsEvents
import org.closs.shared.settings.presentation.state.SettingsState
import org.closs.shared.settings.presentation.viewmodel.SettingsViewModel

class DefaultSettingsViewModel(
    override val repository: SettingsRepository,
    override val navigator: Navigator,
    override val handle: SavedStateHandle
) : SettingsViewModel(
    repository = repository,
    navigator = navigator,
    handle = handle
) {
    private val _state: MutableStateFlow<SettingsState> = MutableStateFlow(SettingsState())
    override val state: StateFlow<SettingsState> = _state.asStateFlow()

    override fun onEvent(event: SettingsEvents) {
    }
}
