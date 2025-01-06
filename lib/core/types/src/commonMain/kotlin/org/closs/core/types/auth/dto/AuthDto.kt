package org.closs.core.shared.types.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.closs.core.shared.types.user.UserDto

@Serializable
data class AuthDto(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("user")
    val user: UserDto,
)
