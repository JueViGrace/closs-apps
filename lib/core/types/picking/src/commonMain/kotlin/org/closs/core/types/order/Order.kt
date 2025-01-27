package org.closs.core.types.order

import org.closs.core.types.order.dto.OrderLineDto

data class Order(
    val agencia: String,
    val tipodoc: String,
    val documento: String,
    val nombrecli: String,
    val codcliente: String,
    val emision: String,
    val upickup: String,
    val idcarrito: String,
    val almacen: String,
    val lines: List<OrderLineDto> = emptyList()
)
