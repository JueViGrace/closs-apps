package org.closs.core.types.order.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    @SerialName("agencia")
    val agencia: String,
    @SerialName("tipodoc")
    val tipodoc: String,
    @SerialName("documento")
    val documento: String,
    @SerialName("nombrecli")
    val nombrecli: String,
    @SerialName("codcliente")
    val codcliente: String,
    @SerialName("emision")
    val emision: String,
    @SerialName("upickup")
    val upickup: String,
    @SerialName("idcarrito")
    val idcarrito: String,
    @SerialName("almacen")
    val almacen: String,
    @SerialName("ruta_codigo")
    val rutaCodigo: String,
    @SerialName("ruta_descrip")
    val rutaDescrip: String,
    @SerialName("ke_pedstatus")
    val kePedStatus: String,
    @SerialName("lines")
    val lines: List<OrderLineDto>
)
