package org.closs.auth.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.closs.auth.shared.data.AuthRepository
import org.closs.auth.shared.presentation.events.SignInEvents
import org.closs.auth.shared.presentation.viewmodel.SignInViewModel
import org.closs.core.presentation.shared.messages.Messages
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.unexpected_error
import org.closs.core.types.shared.auth.dto.SignInDto
import org.closs.core.types.shared.state.RequestState
import org.closs.core.types.shared.state.ResponseMessage

class DefaultSignInViewModel(
    override val navigator: Navigator,
    override val messages: Messages,
    override val authRepository: AuthRepository,
) : SignInViewModel(
    navigator = navigator,
    messages = messages,
    authRepository = authRepository
) {
    override val state = _state.asStateFlow()

    override fun onEvent(event: SignInEvents) {
        when (event) {
            SignInEvents.OnNavigateToForgotPassword -> navigateForgotPassword()
            is SignInEvents.OnUsernameChanged -> signInUsernameChanged(event.value)
            is SignInEvents.OnPasswordChanged -> signInPasswordChanged(event.value)
            SignInEvents.TogglePasswordVisibility -> togglePasswordVisibility()
            SignInEvents.OnSignInSubmit -> signInSubmit()
            else -> {}
        }
    }

    // todo: loading state is not shown in ui
    override fun signInSubmit() {
        if (onSignInError()) return
        viewModelScope.launch {
            authRepository.signIn(
                signInDto = SignInDto(
                    username = _state.value.username,
                    password = _state.value.password
                )
            ).collect { call ->
                when (call) {
                    is RequestState.Error -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                errorMessage = Res.string.unexpected_error
                            )
                        }
                        messages.sendMessage(
                            ResponseMessage(
                                message = Res.string.unexpected_error,
                                description = call.error
                            )
                        )
                    }
                    is RequestState.Success -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                errorMessage = null
                            )
                        }

                        messages.sendMessage(call.data.response)
                        navigator.navigate(
                            destination = Destination.Home,
                            navOptions = NavOptions.Builder().apply {
                                setPopUpTo(route = Destination.SignIn, inclusive = true)
                                setLaunchSingleTop(true)
                            }.build()
                        )

                        resetState()
                    }
                    else -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = true,
                                errorMessage = null
                            )
                        }
                    }
                }
            }
        }
    }
}
