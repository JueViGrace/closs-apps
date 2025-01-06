package org.closs.core.shared.types.product

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UpdateProductDto(
    @SerialName("codigo")
    val codigo: String,
    @SerialName("grupo")
    val grupo: String,
    @SerialName("subgrupo")
    val subgrupo: String,
    @SerialName("nombre")
    val nombre: String,
    @SerialName("referencia")
    val referencia: String,
    @SerialName("marca")
    val marca: String,
    @SerialName("unidad")
    val unidad: String,
    @SerialName("discont")
    val discont: Long,
    @SerialName("existencia")
    val existencia: Long,
    @SerialName("vta_max")
    val vtaMax: Long,
    @SerialName("vta_min")
    val vtaMin: Long,
    @SerialName("vta_minenx")
    val vtaMinEx: Long,
    @SerialName("comprometido")
    val comprometido: Long,
    @SerialName("precio1")
    val precio1: Double,
    @SerialName("precio2")
    val precio2: Double,
    @SerialName("precio3")
    val precio3: Double,
    @SerialName("precio4")
    val precio4: Double,
    @SerialName("precio5")
    val precio5: Double,
    @SerialName("precio6")
    val precio6: Double,
    @SerialName("precio7")
    val precio7: Double,
    @SerialName("preventa")
    val preventa: Long,
    @SerialName("vta_solofac")
    val vtaSoloFac: Long,
    @SerialName("vta_solone")
    val vtaSoloNe: Long,
    @SerialName("codbarras")
    val codBarras: Long,
    @SerialName("detalles")
    val detalles: String,
    @SerialName("cantbulto")
    val cantBulto: Double,
    @SerialName("costo_prom")
    val costoProm: Double,
    @SerialName("util1")
    val util1: String,
    @SerialName("util2")
    val util2: String,
    @SerialName("util3")
    val util3: String,
    @SerialName("fchultcomp")
    val fchUltComp: String,
    @SerialName("qtyultcomp")
    val qtyUltComp: String,
    @SerialName("images")
    val images: List<String>
)
