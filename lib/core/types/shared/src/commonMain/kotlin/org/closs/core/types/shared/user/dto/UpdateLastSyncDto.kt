package org.closs.core.types.shared.user.dto

import kotlinx.serialization.SerialName

data class UpdateLastSyncDto(
    @SerialName("id")
    val id: String,
    @SerialName("last_sync")
    val lastSync: String,
    @SerialName("version")
    val version: String
)
