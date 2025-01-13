package org.closs.core.shared.types.user

import kotlinx.serialization.SerialName

data class UpdateLastSyncDto(
    @SerialName("id")
    val id: String,
    @SerialName("last_sync")
    val lastSync: String,
    @SerialName("version")
    val version: String
)
