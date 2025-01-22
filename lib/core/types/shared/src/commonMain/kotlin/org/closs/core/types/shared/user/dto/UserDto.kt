package org.closs.core.types.shared.user.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("id")
    val id: String,
    @SerialName("username")
    val username: String,
    @SerialName("code")
    val code: String,
    @SerialName("last_sync")
    val lastSync: String,
    @SerialName("version")
    val version: String,
    @SerialName("createdAt")
    val createdAt: String,
    @SerialName("updatedAt")
    val updatedAt: String,
)
