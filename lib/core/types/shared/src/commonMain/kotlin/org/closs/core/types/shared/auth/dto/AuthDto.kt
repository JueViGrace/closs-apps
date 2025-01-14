package org.closs.core.types.shared.auth.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.closs.core.types.shared.user.dto.UserDto

@Serializable
data class AuthDto(
    @SerialName("accessToken")
    val accessToken: String,
    @SerialName("refreshToken")
    val refreshToken: String,
    @SerialName("user")
    val user: UserDto,
)
