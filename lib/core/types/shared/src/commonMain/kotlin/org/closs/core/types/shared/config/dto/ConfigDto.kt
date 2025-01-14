package org.closs.core.types.shared.config.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigDto(
    @SerialName("cnfg_idconfig")
    val idConfig: String,
    @SerialName("cnfg_clase")
    val clase: String,
    @SerialName("cnfg_tipo")
    val tipo: String,
    @SerialName("cnfg_valnum")
    val valNum: Double,
    @SerialName("cnfg_valsino")
    val valSino: Int,
    @SerialName("cnfg_valtxt")
    val valTxt: String,
    @SerialName("cnfg_lentxt")
    val lenTxt: Int,
    @SerialName("cnfg_valfch")
    val valFch: String,
    @SerialName("cnfg_activa")
    val activa: Int,
    @SerialName("cnfg_etiq")
    val etiq: String,
    @SerialName("cnfg_ttip")
    val ttip: String,
    @SerialName("username")
    val username: String,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
)
