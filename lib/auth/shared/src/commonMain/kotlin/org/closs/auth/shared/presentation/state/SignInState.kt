package org.closs.auth.shared.presentation.state

import org.jetbrains.compose.resources.StringResource

data class SignInState(
    // Memory values
    val companyResp: String = "",

    // Field values
    val company: String = "",
    val username: String = "",
    val password: String = "",

    // Error messages
    val companyError: StringResource? = null,
    val usernameError: StringResource? = null,
    val passwordError: StringResource? = null,
    val errorMessage: StringResource? = null,

    // Screen state
    val passwordVisibility: Boolean = false,
    val companyEnabled: Boolean = false,
    val signInEnabled: Boolean = false,
    val isLoading: Boolean = false,
)
