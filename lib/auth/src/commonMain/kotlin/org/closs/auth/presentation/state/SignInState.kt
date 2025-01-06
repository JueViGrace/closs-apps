package org.closs.auth.presentation.state

import org.jetbrains.compose.resources.StringResource

data class SignInState(
    // Field values
    val username: String = "",
    val password: String = "",

    // Error messages
    val usernameError: StringResource? = null,
    val passwordError: StringResource? = null,
    val errorMessage: StringResource? = null,

    // Screen state
    val passwordVisibility: Boolean = false,
    val signInEnabled: Boolean = false,
    val isLoading: Boolean = false,
)
