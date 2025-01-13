package org.closs.core.shared.types.order

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderDetailsDto(
    @SerialName("kti_tdoc")
    val ktiTdoc: String,
    @SerialName("kti_ndoc")
    val ktiNdoc: String,
    @SerialName("kti_tipprec")
    val ktiTipprec: String,
    @SerialName("kmv_codart")
    val kmvCodart: String,
    @SerialName("kmv_nombre")
    val kmvNombre: String,
    @SerialName("kmv_cant")
    val kmvCant: Int,
    @SerialName("kmv_artprec")
    val kmvArtprec: Double,
    @SerialName("kmv_stot")
    val kmvStot: Double,
    @SerialName("kmv_dctolin")
    val kmvDctolin: Double,
)
