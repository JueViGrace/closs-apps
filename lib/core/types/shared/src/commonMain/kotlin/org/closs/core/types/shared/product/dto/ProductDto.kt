package org.closs.core.types.shared.product.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    @SerialName("agencia")
    val agencia: String = "",
    @SerialName("codigo")
    val codigo: String,
    @SerialName("grupo")
    val grupo: String = "",
    @SerialName("subgrupo")
    val subgrupo: String = "",
    @SerialName("nombre")
    val nombre: String,
    @SerialName("referencia")
    val referencia: String,
    @SerialName("marca")
    val marca: String,
    @SerialName("unidad")
    val unidad: String,
    @SerialName("discont")
    val discont: Long = 0,
    @SerialName("existencia")
    val existencia: Long = 0,
    @SerialName("vta_max")
    val vtaMax: Long = 0,
    @SerialName("vta_min")
    val vtaMin: Long = 0,
    @SerialName("vta_minenx")
    val vtaMinEx: Long = 0,
    @SerialName("comprometido")
    val comprometido: Long = 0,
    @SerialName("precio1")
    val precio1: Double = 0.0,
    @SerialName("precio2")
    val precio2: Double = 0.0,
    @SerialName("precio3")
    val precio3: Double = 0.0,
    @SerialName("precio4")
    val precio4: Double = 0.0,
    @SerialName("precio5")
    val precio5: Double = 0.0,
    @SerialName("precio6")
    val precio6: Double = 0.0,
    @SerialName("precio7")
    val precio7: Double = 0.0,
    @SerialName("preventa")
    val preventa: Long = 0,
    @SerialName("vta_solofac")
    val vtaSoloFac: Long = 0,
    @SerialName("vta_solone")
    val vtaSolOne: Long = 0,
    @SerialName("codbarras")
    val codBarras: Long = 0,
    @SerialName("detalles")
    val detalles: String = "",
    @SerialName("cantbulto")
    val cantBulto: Double = 0.0,
    @SerialName("costo_prom")
    val costoProm: Double = 0.0,
    @SerialName("util1")
    val util1: String = "",
    @SerialName("util2")
    val util2: String = "",
    @SerialName("util3")
    val util3: String = "",
    @SerialName("fchultcomp")
    val fchUltComp: String = "",
    @SerialName("qtyultcomp")
    val qtyUltComp: String = "",
    @SerialName("image")
    val image: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String = "",
)
