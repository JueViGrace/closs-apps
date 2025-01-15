package org.closs.auth.shared.presentation.events

sealed interface SignInEvents {
    // UI Events
    data class OnCompanyChanged(val value: String) : SignInEvents
    data object OnCompanySubmitted : SignInEvents
    data class OnUsernameChanged(val value: String) : SignInEvents
    data class OnPasswordChanged(val value: String) : SignInEvents
    data object TogglePasswordVisibility : SignInEvents

    // Navigation Events
    data object OnSignInSubmit : SignInEvents
    data object OnNavigateToForgotPassword : SignInEvents
    data object OnNavigateToAccounts : SignInEvents
}
