package org.closs.core.shared.types.document

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DocumentDto(
    @SerialName("agencia")
    val agencia: String,
    @SerialName("tipodoc")
    val tipodoc: String,
    @SerialName("documento")
    val documento: String,
    @SerialName("tipodocv")
    val tipodocv: String,
    @SerialName("codcliente")
    val codcliente: String,
    @SerialName("nombrecli")
    val nombrecli: String,
    @SerialName("contribesp")
    val contribesp: Int,
    @SerialName("ruta_parme")
    val rutaParme: Int,
    @SerialName("tipoprecio")
    val tipoprecio: Int,
    @SerialName("emision")
    val emision: String,
    @SerialName("recepcion")
    val recepcion: String,
    @SerialName("vence")
    val vence: String,
    @SerialName("diascred")
    val diascred: Double,
    @SerialName("estatusdoc")
    val estatusdoc: Int,
    @SerialName("dtotneto")
    val dtotneto: Double,
    @SerialName("dtotimpuest")
    val dtotimpuest: Double,
    @SerialName("dtotalfinal")
    val dtotalfinal: Double,
    @SerialName("dtotpagos")
    val dtotpagos: Double,
    @SerialName("dtotdescuen")
    val dtotdescuen: Double,
    @SerialName("dFlete")
    val dFlete: Double,
    @SerialName("dtotdev")
    val dtotdev: Double,
    @SerialName("dvndmtototal")
    val dvndmtototal: Double,
    @SerialName("dretencion")
    val dretencion: Double,
    @SerialName("dretencioniva")
    val dretencioniva: Double,
    @SerialName("vendedor")
    val vendedor: String,
    @SerialName("codcoord")
    val codcoord: String,
    @SerialName("aceptadev")
    val aceptadev: Int,
    @SerialName("kti_negesp")
    val ktiNegesp: Int,
    @SerialName("bsiva")
    val bsiva: Double,
    @SerialName("bsflete")
    val bsflete: Double,
    @SerialName("bsretencion")
    val bsretencion: Double,
    @SerialName("bsretencioniva")
    val bsretencioniva: Double,
    @SerialName("tasadoc")
    val tasadoc: Double,
    @SerialName("mtodcto")
    val mtodcto: Double,
    @SerialName("fchvencedcto")
    val fchvencedcto: String,
    @SerialName("tienedcto")
    val tienedcto: Int,
    @SerialName("cbsret")
    val cbsret: Double,
    @SerialName("cdret")
    val cdret: Double,
    @SerialName("cbsretiva")
    val cbsretiva: Double,
    @SerialName("cdretiva")
    val cdretiva: Double,
    @SerialName("cbsrparme")
    val cbsrparme: Double,
    @SerialName("cdrparme")
    val cdrparme: Double,
    @SerialName("cbsretflete")
    val cbsretflete: Double,
    @SerialName("cdretflete")
    val cdretflete: Double,
    @SerialName("bsmtoiva")
    val bsmtoiva: Double,
    @SerialName("bsmtofte")
    val bsmtofte: Double,
    @SerialName("retmun_mto")
    val retmunMto: Double,
    @SerialName("dolarflete")
    val dolarflete: Int,
    @SerialName("bsretflete")
    val bsretflete: Double,
    @SerialName("dretflete")
    val dretflete: Double,
    @SerialName("dretmun_mto")
    val dretmunMto: Double,
    @SerialName("retivaoblig")
    val retivaoblig: Int,
    @SerialName("edoentrega")
    val edoentrega: Int,
    @SerialName("dmtoiva")
    val dmtoiva: Double,
    @SerialName("prcdctoaplic")
    val prcdctoaplic: Double,
    @SerialName("montodctodol")
    val montodctodol: Double,
    @SerialName("montodctobs")
    val montodctobs: Double,
    @SerialName("created_at")
    val createdAt: String,
    @SerialName("updated_at")
    val updatedAt: String,
    @SerialName("lines")
    val lines: List<DocumentDetailsDto> = emptyList()
)
