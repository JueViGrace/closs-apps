package org.closs.core.types.customer.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CustomerDto(
    @SerialName("codigo")
    val codigo: String,
    @SerialName("nombre")
    val nombre: String,
    @SerialName("email")
    val email: String,
    @SerialName("direccion")
    val direccion: String,
    @SerialName("telefonos")
    val telefonos: String,
    @SerialName("perscont")
    val perscont: String,
    @SerialName("vendedor")
    val vendedor: String,
    @SerialName("contribspecial")
    val contribspecial: Int,
    @SerialName("status")
    val status: Int,
    @SerialName("sector")
    val sector: String,
    @SerialName("subsector")
    val subsector: String,
    @SerialName("precio")
    val precio: Int,
    @SerialName("kne_activa")
    val kneActiva: Int,
    @SerialName("noemifac")
    val noemifac: Int,
    @SerialName("noeminota")
    val noeminota: Int,
    @SerialName("fchultvta")
    val fchultvta: String,
    @SerialName("mtoultvta")
    val mtoultvta: Double,
    @SerialName("prcdpagdia")
    val prcdpagdia: Double,
    @SerialName("promdiasp")
    val promdiasp: Double,
    @SerialName("riesgocrd")
    val riesgocrd: Double,
    @SerialName("cantdocs")
    val cantdocs: Double,
    @SerialName("totmtodocs")
    val totmtodocs: Double,
    @SerialName("prommtodoc")
    val prommtodoc: Double,
    @SerialName("diasultvta")
    val diasultvta: Double,
    @SerialName("promdiasvta")
    val promdiasvta: Double,
    @SerialName("limcred")
    val limcred: Double,
    @SerialName("fchcrea")
    val fchcrea: String,
    @SerialName("dolarflete")
    val dolarflete: Int,
    @SerialName("nodolarflete")
    val nodolarflete: Int,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
)
