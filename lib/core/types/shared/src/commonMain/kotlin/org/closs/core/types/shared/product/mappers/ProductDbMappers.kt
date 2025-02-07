package org.closs.core.types.shared.product.mappers

import org.closs.core.types.shared.aliases.DbProduct
import org.closs.core.types.shared.product.Product

fun DbProduct.toProduct(): Product = Product(
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
    image = images,
    createdAt = created_at
)
