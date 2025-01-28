package org.closs.core.types.order

import org.closs.core.database.FindOrder
import org.closs.core.types.aliases.DbOrder
import org.closs.core.types.aliases.DbOrderLine
import org.closs.core.types.order.dto.OrderDto
import org.closs.core.types.order.dto.OrderLineDto

fun OrderDto.toClossOrder(userId: String): DbOrder = DbOrder(
    agencia = agencia,
    tipodoc = tipodoc,
    documento = documento,
    codcliente = codcliente,
    nombrecli = nombrecli,
    emision = emision,
    upickup = upickup,
    idcarrito = idcarrito,
    almacen = almacen,
    ke_pedstatus = kePedStatus,
    user_id = userId
)

fun OrderLineDto.toClossOrderLine(userId: String): DbOrderLine = DbOrderLine(
    agencia = agencia,
    tipodoc = tipodoc,
    documento = documento,
    codigo = codigo,
    nombre = nombre,
    almacen = almacen,
    cantref = cantref.toLong(),
    cantidad = cantidad.toLong(),
    user_id = userId
)

fun DbOrder.toOrder(): Order = Order(
    agencia = agencia,
    tipodoc = tipodoc,
    documento = documento,
    codcliente = codcliente,
    nombrecli = nombrecli,
    emision = emision,
    upickup = upickup,
    idcarrito = idcarrito,
    almacen = almacen,
    kePedStatus = ke_pedstatus,
    lines = emptyList()
)

fun List<FindOrder>.toOrderWithLines(): Order? {
    val group: Map<Order, List<FindOrder>> = this.groupBy { row ->
        Order(
            agencia = row.agencia,
            tipodoc = row.tipodoc,
            documento = row.documento,
            codcliente = row.codcliente,
            nombrecli = row.nombrecli,
            emision = row.emision,
            upickup = row.upickup,
            idcarrito = row.idcarrito,
            almacen = row.almacen,
            kePedStatus = row.ke_pedstatus
        )
    }

    val order: Order? = group.map { (key: Order, rows: List<FindOrder>) ->
        return@map Order(
            agencia = key.agencia,
            tipodoc = key.tipodoc,
            documento = key.documento,
            codcliente = key.codcliente,
            nombrecli = key.nombrecli,
            emision = key.emision,
            upickup = key.upickup,
            idcarrito = key.idcarrito,
            almacen = key.almacen,
            kePedStatus = key.kePedStatus,
            lines = rows.toOrderLines()
        )
    }.first()
    return order
}

fun List<FindOrder>.toOrderLines(): List<OrderLine> {
    if (this.any { row -> row.documento_ == null }) return emptyList()

    val details: List<OrderLine> = this.map { row ->
        return@map OrderLine(
            agencia = row.agencia_ ?: "",
            tipodoc = row.tipodoc_ ?: "",
            documento = row.documento_ ?: "",
            codigo = row.codigo ?: "",
            nombre = row.nombre ?: "",
            almacen = row.almacen_ ?: "",
            cantref = row.cantref?.toInt() ?: 0,
            cantidad = row.cantidad?.toInt() ?: 0,
        )
    }
    return details
}
