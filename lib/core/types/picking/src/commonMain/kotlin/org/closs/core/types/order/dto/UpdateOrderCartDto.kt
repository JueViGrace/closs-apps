package org.closs.core.types.order.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateOrderCartDto(
    @SerialName("documento")
    val documento: String,
    @SerialName("upickup")
    val upickup: String,
    @SerialName("idcarrito")
    val idcarrito: String,
)
