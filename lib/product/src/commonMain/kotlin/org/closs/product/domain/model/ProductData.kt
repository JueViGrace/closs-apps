package org.closs.product.domain.model

data class ProductData(
    val name: String,
    val description: String,
    val category: String,
    val price: Double,
    val stock: Int,
    val issued: Int,
    val hasStock: Boolean,
    val discount: Double,
    val rating: Double,
)
