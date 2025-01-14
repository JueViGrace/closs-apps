package org.closs.core.types.document.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DocumentDetailsDto(
    @SerialName("agencia")
    val agencia: String,
    @SerialName("tipodoc")
    val tipodoc: String,
    @SerialName("documento")
    val documento: String,
    @SerialName("tipodocv")
    val tipodocv: String,
    @SerialName("grupo")
    val grupo: String,
    @SerialName("subgrupo")
    val subgrupo: String,
    @SerialName("origen")
    val origen: Int,
    @SerialName("codigo")
    val codigo: String,
    @SerialName("codhijo")
    val codhijo: String,
    @SerialName("pid")
    val pid: String,
    @SerialName("nombre")
    val nombre: String,
    @SerialName("cantidad")
    val cantidad: Int,
    @SerialName("cntdevuelt")
    val cntdevuelt: Int,
    @SerialName("vndcntdevuelt")
    val vndcntdevuelt: Int,
    @SerialName("dvndmtototal")
    val dvndmtototal: Double,
    @SerialName("dpreciofin")
    val dpreciofin: Double,
    @SerialName("dpreciounit")
    val dpreciounit: Double,
    @SerialName("dmontoneto")
    val dmontoneto: Double,
    @SerialName("dmontototal")
    val dmontototal: Double,
    @SerialName("timpueprc")
    val timpueprc: Double,
    @SerialName("unidevuelt")
    val unidevuelt: Int,
    @SerialName("fechadoc")
    val fechadoc: String,
    @SerialName("vendedor")
    val vendedor: String,
    @SerialName("codcoord")
    val codcoord: String,
)
