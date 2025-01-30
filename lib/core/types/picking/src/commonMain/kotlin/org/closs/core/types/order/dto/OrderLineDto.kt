package org.closs.core.types.order.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.closs.core.types.shared.product.dto.ProductDto

@Serializable
data class OrderLineDto(
    @SerialName("agencia")
    val agencia: String,
    @SerialName("tipodoc")
    val tipodoc: String,
    @SerialName("documento")
    val documento: String,
    @SerialName("almacen")
    val almacen: String,
    @SerialName("product")
    val productDto: ProductDto,
    @SerialName("cantref")
    val cantref: Int,
    @SerialName("cantidad")
    val cantidad: Int,
)
