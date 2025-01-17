package org.closs.core.types.order.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OrderDto(
    @SerialName("kti_ndoc")
    val ktiNdoc: String,
    @SerialName("kti_tdoc")
    val ktiTdoc: String,
    @SerialName("kti_codcli")
    val ktiCodcli: String,
    @SerialName("kti_nombrecli")
    val ktiNombrecli: String,
    @SerialName("kti_codven")
    val ktiCodven: String,
    @SerialName("kti_docsol")
    val ktiDocsol: String,
    @SerialName("kti_condicion")
    val ktiCondicion: String,
    @SerialName("kti_tipprec")
    val ktiTipprec: Int,
    @SerialName("kti_totneto")
    val ktiTotneto: String,
    @SerialName("kti_status")
    val ktiStatus: Int,
    @SerialName("kti_nroped")
    val ktiNroped: String,
    @SerialName("kti_fchdoc")
    val ktiFchdoc: String,
    @SerialName("kti_negesp")
    val ktiNegesp: Int,
    @SerialName("ke_pedstatus")
    val kePedstatus: Int,
    @SerialName("dolarflete")
    val dolarFlete: Int,
    @SerialName("complemento")
    val complemento: Int,
    @SerialName("nro_complemento")
    val nroComplemento: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("details")
    val details: List<OrderDetailsDto>
)
