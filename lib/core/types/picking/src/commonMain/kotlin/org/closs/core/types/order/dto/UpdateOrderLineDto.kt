package org.closs.core.types.order.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateOrderLineDto(
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
    @SerialName("cantidad")
    val cantidad: Int,
)
