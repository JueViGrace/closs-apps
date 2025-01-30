package org.closs.core.types.shared.product

data class Product(
    val agencia: String = "",
    val grupo: String = "",
    val subGrupo: String = "",
    val nombre: String = "",
    val codigo: String = "",
    val referencia: String = "",
    val marca: String = "",
    val unidad: String = "",
    val existencia: Int = 0,
    val comprometido: Int = 0,
    val image: String = "",
    val createdAt: String = "",
)
