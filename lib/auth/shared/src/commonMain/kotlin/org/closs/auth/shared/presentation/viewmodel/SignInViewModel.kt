package org.closs.auth.shared.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.closs.auth.shared.data.repository.AuthRepository
import org.closs.auth.shared.domain.model.SignIn
import org.closs.auth.shared.domain.rules.AuthValidator
import org.closs.auth.shared.presentation.events.SignInEvents
import org.closs.auth.shared.presentation.state.SignInState
import org.closs.core.presentation.shared.messages.Messages
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.Navigator

abstract class SignInViewModel(
    protected open val navigator: Navigator,
    protected open val messages: Messages,
    protected open val authRepository: AuthRepository
) : ViewModel() {
    protected open val _state: MutableStateFlow<SignInState> = MutableStateFlow(SignInState())
    open val state: StateFlow<SignInState> = MutableStateFlow(SignInState()).asStateFlow()

    abstract fun onEvent(event: SignInEvents)

    protected fun navigateForgotPassword() {
        resetState()
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.ForgotPassword,
                navOptions = NavOptions.Builder().apply {
                    setPopUpTo(route = Destination.SignIn, inclusive = false)
                    setLaunchSingleTop(true)
                }.build()
            )
        }
    }

    protected fun signInUsernameChanged(value: String) {
        _state.update { state ->
            state.copy(
                username = value,
                signInSubmitEnabled = state.username.isNotEmpty() && state.password.isNotEmpty()
            )
        }
    }

    protected fun signInPasswordChanged(value: String) {
        _state.update { state ->
            state.copy(
                password = value,
                signInSubmitEnabled = state.username.isNotEmpty() && state.password.isNotEmpty()
            )
        }
    }

    protected fun togglePasswordVisibility() {
        _state.update { state ->
            state.copy(
                passwordVisibility = !state.passwordVisibility
            )
        }
    }

    protected abstract fun signInSubmit()

    protected fun onSignInError(): Boolean {
        val validation = AuthValidator.validateSignIn(
            signIn = SignIn(
                username = _state.value.username,
                password = _state.value.password,
            )
        )
        val errors = listOfNotNull(
            validation.usernameError,
            validation.passwordError
        )
        _state.update { state ->
            state.copy(
                usernameError = validation.usernameError,
                passwordError = validation.passwordError
            )
        }
        return errors.isNotEmpty()
    }

    protected fun resetState() {
        _state.update { state ->
            state.copy(
                company = "",
                username = "",
                password = "",
                companyError = null,
                usernameError = null,
                passwordError = null,
                passwordVisibility = false,
                signInSubmitEnabled = false,
                errorMessage = null,
                isLoading = false
            )
        }
    }
}
