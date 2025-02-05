package org.closs.picking.app.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.closs.core.presentation.shared.messages.Messages
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.types.shared.state.RequestState
import org.closs.shared.app.data.AppRepository
import org.closs.shared.app.presentation.state.AppState
import org.closs.shared.app.presentation.viewmodel.AppViewModel

class DefaultAppViewModel(
    override val navigator: Navigator,
    override val messages: Messages,
    override val handle: SavedStateHandle,
    override val appRepository: AppRepository,
) : AppViewModel(
    navigator = navigator,
    messages = messages,
    handle = handle,
    appRepository = appRepository
) {
    private val _state: MutableStateFlow<AppState> = MutableStateFlow(AppState())
    override val state = combine(
        _state,
        appRepository.validateSession(),
    ) { state, session ->
        when (session) {
            is RequestState.Error -> {
                navigator.navigate(
                    destination = Destination.SignIn,
                    navOptions = NavOptions.Builder().apply {
                        setPopUpTo(route = Destination.Splash, inclusive = true)
                        setLaunchSingleTop(true)
                    }.build()
                )
                state.copy(
                    session = null,
                )
            }
            is RequestState.Success -> {
                navigator.navigate(
                    destination = Destination.Home,
                    navOptions = NavOptions.Builder().apply {
                        setPopUpTo(route = Destination.Splash, inclusive = true)
                        setLaunchSingleTop(true)
                    }.build()
                )
                state.copy(
                    session = session.data,
                )
            }
            else -> {
                state.copy(
                    session = null,
                    snackMessage = null,
                    description = ""
                )
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _state.value
    )
}
