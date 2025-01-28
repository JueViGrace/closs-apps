package org.closs.core.types.order.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderLineDto(
    @SerialName("agencia")
    val agencia: String,
    @SerialName("tipodoc")
    val tipodoc: String,
    @SerialName("documento")
    val documento: String,
    @SerialName("almacen")
    val almacen: String,
    @SerialName("codigo")
    val codigo: String,
    @SerialName("nombre")
    val nombre: String,
    @SerialName("cantref")
    val cantref: Int,
    @SerialName("cantidad")
    val cantidad: Int,
)
