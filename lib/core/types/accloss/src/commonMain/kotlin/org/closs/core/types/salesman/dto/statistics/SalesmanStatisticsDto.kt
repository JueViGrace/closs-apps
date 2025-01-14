package org.closs.core.types.salesman.dto.statistics

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SalesmanStatisticsDto(
    @SerialName("codcoord")
    val codcoord: String,
    @SerialName("nomcoord")
    val nomcoord: String,
    @SerialName("vendedor")
    val vendedor: String,
    @SerialName("nombrevend")
    val nombrevend: String,
    @SerialName("cntpedidos")
    val cntpedidos: Int,
    @SerialName("mtopedidos")
    val mtopedidos: Double,
    @SerialName("cntfacturas")
    val cntfacturas: Int,
    @SerialName("mtofacturas")
    val mtofacturas: Double,
    @SerialName("metavend")
    val metavend: Double,
    @SerialName("prcmeta")
    val prcmeta: Double,
    @SerialName("cntclientes")
    val cntclientes: Int,
    @SerialName("clivisit")
    val clivisit: Int,
    @SerialName("prcvisitas")
    val prcvisitas: Double,
    @SerialName("lom_montovtas")
    val lomMontovtas: Double,
    @SerialName("lom_prcvtas")
    val lomPrcvtas: Double,
    @SerialName("lom_prcvisit")
    val lomPrcvisit: Double,
    @SerialName("rlom_montovtas")
    val rlomMontovtas: Double,
    @SerialName("rlom_prcvtas")
    val rlomPrcvtas: Double,
    @SerialName("rlom_prcvisit")
    val rlomPrcvisit: Double,
    @SerialName("fecha_estad")
    val fechaEstad: String,
    @SerialName("ppgdol_totneto")
    val ppgdolTotneto: Double,
    @SerialName("devdol_totneto")
    val devdolTotneto: Double,
    @SerialName("defdol_totneto")
    val defdolTotneto: Double,
    @SerialName("totdolcob")
    val totdolcob: Double,
    @SerialName("cntrecl")
    val cntrecl: Int,
    @SerialName("mtorecl")
    val mtorecl: Double,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
)
