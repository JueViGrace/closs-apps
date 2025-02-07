package org.closs.core.types.order.mappers

import org.closs.core.types.aliases.DbOrder
import org.closs.core.types.aliases.DbOrderLine
import org.closs.core.types.order.Order
import org.closs.core.types.order.OrderLine
import org.closs.core.types.order.dto.OrderDto
import org.closs.core.types.order.dto.OrderLineDto
import org.closs.core.types.shared.product.mappers.toProduct

fun OrderDto.toDbOrder(userId: String): DbOrder = DbOrder(
    agencia = agencia,
    tipodoc = tipodoc,
    documento = documento,
    codcliente = codcliente,
    nombrecli = nombrecli,
    emision = emision,
    upickup = upickup,
    idcarrito = idcarrito,
    pickStartedAt = pickStartedAt,
    pickEndedAt = pickEndedAt,
    almacen = almacen,
    ruta_codigo = rutaCodigo,
    ruta_descrip = rutaDescrip,
    ke_pedstatus = kePedStatus,
    user_id = userId
)

fun OrderLineDto.toDbOrderLine(userId: String): DbOrderLine = DbOrderLine(
    agencia = agencia,
    tipodoc = tipodoc,
    documento = documento,
    almacen = almacen,
    codigo = productDto.codigo,
    nombre = productDto.nombre,
    cantref = cantref.toLong(),
    cantidad = cantidad.toLong(),
    user_id = userId
)

fun OrderDto.toOrder(): Order {
    return Order(
        agencia = agencia,
        tipodoc = tipodoc,
        documento = documento,
        codcliente = codcliente,
        nombrecli = nombrecli,
        emision = emision,
        upickup = upickup,
        idcarrito = idcarrito,
        almacen = almacen,
        rutaCodigo = rutaCodigo,
        rutaDescrip = rutaDescrip,
        kePedStatus = kePedStatus,
        userId = "",
        lines = lines.toOrderLines()
    )
}

fun List<OrderLineDto>.toOrderLines(): List<OrderLine> {
    val lines: MutableList<OrderLine> = mutableListOf()
    for (line in this) {
        lines.add(
            OrderLine(
                agencia = line.agencia,
                tipodoc = line.tipodoc,
                documento = line.documento,
                product = line.productDto.toProduct(),
                almacen = line.almacen,
                cantref = line.cantref,
                cantidad = line.cantidad,
                userId = ""
            )
        )
    }

    return lines.toList()
}
