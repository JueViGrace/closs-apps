package org.closs.core.shared.types.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignInDto(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
)
