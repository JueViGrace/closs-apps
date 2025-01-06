package org.closs.auth.presentation.state

import org.jetbrains.compose.resources.StringResource

data class ForgotPasswordState(
    // Field values
    val username: String = "",

    // Error message
    val usernameError: StringResource? = null,
    val errorMessage: StringResource? = null,

    // Screen state
    val isLoading: Boolean = false,
)
