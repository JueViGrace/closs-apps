package org.closs.core.types.order.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateOrderDto(
    @SerialName("agencia")
    val agencia: String,
    @SerialName("tipodoc")
    val tipodoc: String,
    @SerialName("documento")
    val documento: String,
    @SerialName("upickup")
    val upickup: String,
    @SerialName("idcarrito")
    val idcarrito: String,
    @SerialName("almacen")
    val almacen: String,
    @SerialName("lines")
    val lines: List<UpdateOrderLineDto>
)
