package org.closs.core.types.shared.order

data class Order(
    val id: String,
    val totalAmount: Double,
    val paymentMethod: String,
    val status: String,
    val userId: String,
    val createdAt: String,
    val details: List<OrderDetails>
)
