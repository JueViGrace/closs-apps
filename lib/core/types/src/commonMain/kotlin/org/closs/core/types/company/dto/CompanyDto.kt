package org.closs.core.shared.types.company

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CompanyDto(
    @SerialName("codigo")
    val code: String,
    @SerialName("nombre")
    val name: String,
    @SerialName("status")
    val status: Int,
    @SerialName("enlace")
    val domain: String,
    @SerialName("agen")
    val agency: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
)
