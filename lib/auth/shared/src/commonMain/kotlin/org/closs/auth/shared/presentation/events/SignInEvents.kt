package org.closs.auth.shared.presentation.events

sealed interface SignInEvents {
    // UI Events
    data class OnSignInUsernameChanged(val value: String) : SignInEvents
    data class OnSignInPasswordChanged(val value: String) : SignInEvents
    data object TogglePasswordVisibility : SignInEvents

    // Navigation Events
    data object OnSignInSubmit : SignInEvents
    data object OnNavigateToForgotPassword : SignInEvents
    data object OnNavigateToSignUp : SignInEvents
}
