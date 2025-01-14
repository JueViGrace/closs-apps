package org.closs.core.types.manager.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ManagerDto(
    @SerialName("kng_codgcia")
    val kngCodgcia: String,
    @SerialName("kng_codcoord")
    val kngCodcoord: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
)
