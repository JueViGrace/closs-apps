package org.closs.core.shared.types.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenDto(
    @SerialName("refreshToken")
    val refreshToken: String
)
