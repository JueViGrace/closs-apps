package org.closs.core.types.order

data class OrderLine(
    val agencia: String,
    val tipodoc: String,
    val documento: String,
    val almacen: String,
    val codigo: String,
    val cantref: Double,
    val cantidad: Double,
)
