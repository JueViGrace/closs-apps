package org.closs.core.types.salesman.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SalesmanDto(
    @SerialName("codigo")
    val codigo: String,
    @SerialName("username")
    val username: String,
    @SerialName("nombre")
    val nombre: String,
    @SerialName("email")
    val email: String,
    @SerialName("telefono")
    val telefono: String,
    @SerialName("telefonos")
    val telefonos: String,
    @SerialName("supervpor")
    val supervpor: String,
    @SerialName("sector")
    val sector: String,
    @SerialName("subsector")
    val subsector: String,
    @SerialName("ult_sinc")
    val ultSinc: String,
    @SerialName("version")
    val version: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String
)
