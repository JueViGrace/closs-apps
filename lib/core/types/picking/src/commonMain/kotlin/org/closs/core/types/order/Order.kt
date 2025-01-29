package org.closs.core.types.order

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
    val rutaCodigo: String,
    val rutaDescrip: String,
    val kePedStatus: String,
    val lines: List<OrderLine> = emptyList()
)
