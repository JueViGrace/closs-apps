package org.closs.core.shared.types.user

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateUserDto(
    @SerialName("username")
    val username: String,
    @SerialName("password")
    val password: String,
    @SerialName("codigo")
    val code: String
)
