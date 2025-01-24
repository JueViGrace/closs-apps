package org.closs.core.types.salesman

import org.closs.core.types.aliases.DbSalesman
import org.closs.core.types.salesman.dto.SalesmanDto

fun SalesmanDto.toSalesman(): Salesman = Salesman(
    code = codigo,
    username = username,
    name = nombre,
    email = email,
    phone = telefono,
    phones = telefonos,
    supervisedBy = supervpor,
    sector = sector,
    subSector = subsector,
    lastSync = ultSinc,
    version = version,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun Salesman.toDbSalesman(userId: String): DbSalesman = DbSalesman(
    user_id = userId,
    codigo = code,
    username = username,
    nombre = name,
    email = email,
    telefono = phone,
    telefonos = phones,
    supervpor = supervisedBy,
    sector = sector,
    subsector = subSector,
    ult_sinc = lastSync,
    version = version,
    created_at = createdAt,
    updated_at = updatedAt,
)
