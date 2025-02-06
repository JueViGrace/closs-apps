package org.closs.core.types.order

import org.closs.core.types.shared.product.Product

data class OrderLine(
    val userId: String,
    val agencia: String,
    val tipodoc: String,
    val documento: String,
    val almacen: String,
    val product: Product,
    val cantref: Int,
    val cantidad: Int,
)
