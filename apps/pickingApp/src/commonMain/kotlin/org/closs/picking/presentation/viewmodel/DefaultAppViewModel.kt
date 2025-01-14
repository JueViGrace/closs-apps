package org.closs.picking.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.closs.app.shared.data.AppRepository
import org.closs.app.shared.presentation.state.AppState
import org.closs.app.shared.presentation.viewmodel.AppViewModel
import org.closs.core.presentation.shared.messages.Messages
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.welcome_back
import org.closs.core.types.shared.state.RequestState

class DefaultAppViewModel(
    override val navigator: Navigator,
    override val messages: Messages,
    override val appRepository: AppRepository,
) : AppViewModel(navigator = navigator, messages = messages, appRepository = appRepository) {
    private var _state = MutableStateFlow(AppState())
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
                    snackMessage = session.error.message,
                    description = session.error.description ?: "",
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
                // todo: change for user name
                state.copy(
                    session = session.data,
                    snackMessage = Res.string.welcome_back,
                    description = session.data.user.id
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
        AppState()
    )
}
