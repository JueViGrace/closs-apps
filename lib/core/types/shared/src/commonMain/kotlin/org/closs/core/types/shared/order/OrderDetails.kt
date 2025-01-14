package org.closs.core.types.shared.order

data class OrderDetails(
    val orderId: String,
    val productId: String,
    val totalPrice: Double,
    val quantity: Int,
    val name: String,
    val category: String,
    val price: Double,
    val discount: Double,
    val rating: Double,
    val images: List<String>,
)
