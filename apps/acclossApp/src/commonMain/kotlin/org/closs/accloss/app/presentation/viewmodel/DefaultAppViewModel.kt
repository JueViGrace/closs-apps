package org.closs.accloss.app.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.closs.shared.app.data.AppRepository
import org.closs.shared.app.presentation.state.AppState
import org.closs.shared.app.presentation.viewmodel.AppViewModel
import org.closs.core.presentation.shared.messages.Messages
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.welcome_back
import org.closs.core.types.shared.common.Constants
import org.closs.core.types.shared.state.RequestState

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
    private val showDialog = handle.getStateFlow(Constants.SHOW_HOME_DIALOG_KEY, false)

    private val _state = MutableStateFlow(AppState())
    override val state = combine(
        _state,
        appRepository.validateSession(),
        appRepository.getAccounts()
    ) { state, session, accounts ->
        when (session) {
            is RequestState.Error -> {
                when (accounts) {
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
                            destination = Destination.Accounts,
                            navOptions = NavOptions.Builder().apply {
                                setPopUpTo(route = Destination.Splash, inclusive = true)
                                setLaunchSingleTop(true)
                            }.build()
                        )
                        state.copy(
                            session = null,
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
            }
            is RequestState.Success -> {
                navigator.navigate(
                    destination = Destination.Home,
                    navOptions = NavOptions.Builder().apply {
                        setPopUpTo(route = Destination.Splash, inclusive = true)
                        setLaunchSingleTop(true)
                    }.build()
                )
                // todo: change for user name
                state.copy(
                    session = session.data,
                    snackMessage = Res.string.welcome_back,
                    description = session.data.name
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
        SharingStarted.Lazily,
        _state.value
    )

    override fun toggleDialog() {
        handle[Constants.SHOW_HOME_DIALOG_KEY] = !showDialog.value
    }
}
