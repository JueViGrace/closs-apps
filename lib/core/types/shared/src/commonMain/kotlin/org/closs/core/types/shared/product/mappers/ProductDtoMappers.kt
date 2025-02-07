package org.closs.core.types.shared.product.mappers

import org.closs.core.types.shared.aliases.DbProduct
import org.closs.core.types.shared.product.Product
import org.closs.core.types.shared.product.dto.ProductDto

fun ProductDto.toDbProduct(userId: String): DbProduct = DbProduct(
    agencia = agencia,
    codigo = codigo,
    grupo = grupo,
    subgrupo = subgrupo,
    nombre = nombre,
    referencia = referencia,
    marca = marca,
    unidad = unidad,
    discont = discont,
    existencia = existencia,
    vta_max = vtaMax,
    vta_min = vtaMin,
    vta_minenx = vtaMinEx,
    comprometido = comprometido,
    precio1 = precio1,
    precio2 = precio2,
    precio3 = precio3,
    precio4 = precio4,
    precio5 = precio5,
    precio6 = precio6,
    precio7 = precio7,
    preventa = preventa,
    vta_solofac = vtaSoloFac,
    vta_solone = vtaSolOne,
    codbarras = codBarras,
    detalles = detalles,
    cantbulto = cantBulto,
    costo_prom = costoProm,
    util1 = util1,
    util2 = util2,
    util3 = util3,
    fchultcomp = fchUltComp,
    qtyultcomp = qtyUltComp,
    images = image,
    created_at = createdAt,
    updated_at = updatedAt,
    user_id = userId
)

fun ProductDto.toProduct(): Product = Product(
    agencia = agencia,
    codigo = codigo,
    grupo = grupo,
    subGrupo = subgrupo,
    nombre = nombre,
    referencia = referencia,
    marca = marca,
    unidad = unidad,
    existencia = existencia.toInt(),
    comprometido = comprometido.toInt(),
    image = image,
    createdAt = createdAt,
)
