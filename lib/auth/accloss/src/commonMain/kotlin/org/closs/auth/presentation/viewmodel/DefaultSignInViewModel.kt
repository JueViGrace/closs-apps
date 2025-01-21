package org.closs.auth.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.closs.auth.shared.data.repository.AuthRepository
import org.closs.auth.shared.presentation.events.SignInEvents
import org.closs.auth.shared.presentation.state.SignInState
import org.closs.auth.shared.presentation.viewmodel.SignInViewModel
import org.closs.core.presentation.shared.messages.Messages
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.company_invalid_input
import org.closs.core.types.shared.auth.dto.SignInDto
import org.closs.core.types.shared.state.RequestState

class DefaultSignInViewModel(
    override val navigator: Navigator,
    override val messages: Messages,
    override val authRepository: AuthRepository,
) : SignInViewModel(
    navigator = navigator,
    messages = messages,
    authRepository = authRepository
) {
    override val _state: MutableStateFlow<SignInState> = MutableStateFlow(SignInState(signInEnabled = false))
    override val state = _state.asStateFlow()

    override fun onEvent(event: SignInEvents) {
        when (event) {
            is SignInEvents.OnCompanyChanged -> companyChanged(event.value)
            SignInEvents.OnCompanySubmitted -> companySubmit()
            is SignInEvents.OnUsernameChanged -> signInUsernameChanged(event.value)
            is SignInEvents.OnPasswordChanged -> signInPasswordChanged(event.value)
            SignInEvents.TogglePasswordVisibility -> togglePasswordVisibility()
            SignInEvents.OnSignInSubmit -> signInSubmit()
            SignInEvents.OnNavigateToForgotPassword -> navigateForgotPassword()
            SignInEvents.OnNavigateToAccounts -> navigateToAccounts()
        }
    }

    private fun companyChanged(value: String) {
        _state.update { state ->
            state.copy(
                company = value,
                companySubmitEnabled = state.company.length == 6,
                companyError = if (value.any { !it.isDigit() }) {
                    Res.string.company_invalid_input
                } else {
                    null
                }
            )
        }
    }

    // todo: company search
    // todo: enable login
    private fun companySubmit() {
        _state.update { state ->
            state.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            _state.update { state ->
                state.copy(
                    isLoading = false
                )
            }
        }
    }

    private fun navigateToAccounts() {
        resetState()
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.Accounts,
                navOptions = NavOptions.Builder().apply {
                    setPopUpTo(route = Destination.SignIn, inclusive = false)
                    setLaunchSingleTop(true)
                }.build()
            )
        }
    }

    // todo: save session
    override fun signInSubmit() {
        _state.update { state ->
            state.copy(
                isLoading = true
            )
        }
        if (onSignInError()) {
            _state.update { state ->
                state.copy(
                    isLoading = false
                )
            }
            return
        }
        viewModelScope.launch {
            val call = authRepository.signIn(
                signInDto = SignInDto(
                    username = _state.value.username,
                    password = _state.value.password
                )
            )
            when (call) {
                is RequestState.Error -> {
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            errorMessage = call.error.message
                        )
                    }
                    messages.sendMessage(call.error)
                }
                is RequestState.Success -> {
                    _state.update { state ->
                        state.copy(
                            isLoading = false,
                            errorMessage = null
                        )
                    }

                    messages.sendMessage(call.data)
                    navigator.navigate(
                        destination = Destination.Home,
                        navOptions = NavOptions.Builder().apply {
                            setPopUpTo(route = Destination.AuthGraph, inclusive = true)
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
