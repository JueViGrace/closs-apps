package org.closs.core.types.shared.product

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val category: String,
    val price: Double,
    val stock: Int,
    val issued: Int,
    val hasStock: Boolean,
    val discount: Double,
    val rating: Double,
    val images: List<String>,
)
