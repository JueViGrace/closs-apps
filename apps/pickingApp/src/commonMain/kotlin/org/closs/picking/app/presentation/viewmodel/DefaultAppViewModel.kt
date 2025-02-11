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
import org.closs.core.types.shared.common.Constants.REFRESH_SESSION_KEY
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
    private val _state: MutableStateFlow<AppState> = MutableStateFlow(AppState(refresh = true))

    private val _refresh = combine(
        _state,
        handle.getStateFlow(REFRESH_SESSION_KEY, true)
    ) { state, refresh ->
        state.copy(
            refresh = refresh
        )
    }

    private val _session = appRepository.validateSession()

    private val _refreshSession = combine(
        _refresh,
        _session
    ) { refresh, session ->
        if (refresh.refresh) {
            appRepository.refresh().collect { result ->
                if (result is RequestState.Error) {
                    navigator.navigate(
                        destination = Destination.SignIn,
                        navOptions = NavOptions.Builder().apply {
                            setPopUpTo(route = Destination.Splash, inclusive = true)
                            setLaunchSingleTop(true)
                        }.build()
                    )
                }
            }
        }
        session
    }

    override val state = combine(
        _state,
        _refreshSession,
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
                handle[REFRESH_SESSION_KEY] = false
                state
            }
            is RequestState.Success -> {
                handle[REFRESH_SESSION_KEY] = false
                navigator.navigate(
                    destination = Destination.Home,
                    navOptions = NavOptions.Builder().apply {
                        setPopUpTo(route = Destination.Splash, inclusive = true)
                        setLaunchSingleTop(true)
                    }.build()
                )
                state
            }
            else -> {
                handle[REFRESH_SESSION_KEY] = false
                state.copy(
                    snackMessage = null,
                    description = ""
                )
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.Lazily,
        _state.value
    )
}
