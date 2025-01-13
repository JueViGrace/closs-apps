package org.closs.core.shared.types.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordDto(
    @SerialName("new_password")
    val newPassword: String,
)
