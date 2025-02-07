package org.closs.core.types.order.mappers

import org.closs.core.types.aliases.DbOrder
import org.closs.core.types.order.Order

fun DbOrder.toOrder(): Order = Order(
    userId = user_id,
    agencia = agencia,
    tipodoc = tipodoc,
    documento = documento,
    codcliente = codcliente,
    nombrecli = nombrecli,
    emision = emision,
    upickup = upickup,
    idcarrito = idcarrito,
    almacen = almacen,
    rutaCodigo = ruta_codigo,
    rutaDescrip = ruta_descrip,
    kePedStatus = ke_pedstatus,
    lines = emptyList()
)
