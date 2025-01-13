package org.closs.core.shared.types.salesman

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateSalesmanDto(
    @SerialName("codigo")
    val codigo: String,
    @SerialName("nombre")
    val nombre: String,
    @SerialName("email")
    val email: String,
    @SerialName("telefono")
    val telefono: String,
    @SerialName("telefonos")
    val telefonos: String,
    @SerialName("status")
    val status: Int,
    @SerialName("supervpor")
    val supervpor: String,
    @SerialName("sector")
    val sector: String,
    @SerialName("subcodigo")
    val subcodigo: String,
    @SerialName("nivgcial")
    val nivgcial: Int,
)
