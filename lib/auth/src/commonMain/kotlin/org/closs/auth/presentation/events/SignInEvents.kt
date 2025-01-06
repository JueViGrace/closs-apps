package org.closs.auth.presentation.events

sealed interface SignInEvents {
    // UI Events
    data class OnSignInUsernameChanged(val value: String) :
        org.closs.auth.presentation.events.SignInEvents
    data class OnSignInPasswordChanged(val value: String) :
        org.closs.auth.presentation.events.SignInEvents
    data object TogglePasswordVisibility : org.closs.auth.presentation.events.SignInEvents

    // Navigation Events
    data object OnSignInSubmit : org.closs.auth.presentation.events.SignInEvents
    data object OnNavigateToForgotPassword : org.closs.auth.presentation.events.SignInEvents
    data object OnNavigateToSignUp : org.closs.auth.presentation.events.SignInEvents
}
