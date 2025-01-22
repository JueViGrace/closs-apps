package org.closs.accloss.home.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.types.shared.common.Constants
import org.closs.home.shared.data.HomeRepository
import org.closs.home.shared.presentation.events.HomeEvents
import org.closs.home.shared.presentation.state.HomeState
import org.closs.home.shared.presentation.viewmodel.HomeViewModel

class DefaultHomeViewModel(
    override val repository: HomeRepository,
    override val navigator: Navigator,
    override val handle: SavedStateHandle,
) : HomeViewModel(
    repository = repository,
    navigator = navigator,
    handle = handle
) {
    private val _showDialog = handle.getStateFlow(Constants.SHOW_HOME_DIALOG_KEY, false)

    private val _state = MutableStateFlow(HomeState())
    override val state: StateFlow<HomeState> = combine(
        _state,
        _showDialog
    ) { state, showDialog ->
        state.copy(
            showDialog = showDialog
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _state.value
    )

    override fun onEvent(event: HomeEvents) {
        when (event) {
            HomeEvents.ToggleDialog -> toggleDialog()
            HomeEvents.NavigateToProfile -> navigateToProfile()
            HomeEvents.NavigateToSettings -> navigateToSettings()
            HomeEvents.NavigateToNotifications -> navigateToNotifications()
            HomeEvents.Sync -> sync()
            HomeEvents.LogOut -> logOut()
            else -> {}
        }
    }

    override fun toggleDialog() {
        handle[Constants.SHOW_HOME_DIALOG_KEY] = !_showDialog.value
    }

    override fun sync() {
    }
}
