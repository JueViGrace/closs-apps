package org.closs.auth.shared.domain.rules

import org.jetbrains.compose.resources.StringResource

object AuthValidator {

    fun validateSignIn(signIn: org.closs.auth.domain.model.SignIn): SignInValidationError {
        val result = SignInValidationError()

        return result
    }

    data class SignInValidationError(
        val usernameError: StringResource? = null,
        val passwordError: StringResource? = null,
    )
}
