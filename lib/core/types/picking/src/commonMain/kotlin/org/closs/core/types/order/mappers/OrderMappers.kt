package org.closs.core.types.order.mappers

import org.closs.core.database.FindOrder
import org.closs.core.database.FindOrders
import org.closs.core.types.order.Order
import org.closs.core.types.order.OrderLine
import org.closs.core.types.order.dto.OrderDto
import org.closs.core.types.order.dto.OrderLineDto
import org.closs.core.types.shared.product.Product
import org.closs.core.types.shared.product.mappers.toProductDto

fun Order.toDto(): OrderDto = OrderDto(
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
    rutaCodigo = rutaCodigo,
    rutaDescrip = rutaDescrip,
    kePedStatus = kePedStatus,
    lines = lines.toLineDto()
)

fun List<OrderLine>.toLineDto(): List<OrderLineDto> {
    val lines: MutableList<OrderLineDto> = mutableListOf()
    for (line in this) {
        lines.add(
            OrderLineDto(
                agencia = line.agencia,
                tipodoc = line.tipodoc,
                documento = line.documento,
                productDto = line.product.toProductDto(),
                almacen = line.almacen,
                cantref = line.cantref,
                cantidad = line.cantidad,
            )
        )
    }
    return lines.toList()
}

fun List<FindOrders>.findOrdersToOrder(): List<Order> {
    val group: Map<Order, List<FindOrders>> = this.groupBy { row ->
        Order(
            userId = row.user_id,
            agencia = row.agencia,
            tipodoc = row.tipodoc,
            documento = row.documento,
            codcliente = row.codcliente,
            nombrecli = row.nombrecli,
            emision = row.emision,
            upickup = row.upickup,
            idcarrito = row.idcarrito,
            pickStartedAt = row.pickStartedAt,
            pickEndedAt = row.pickEndedAt,
            almacen = row.almacen,
            rutaCodigo = row.ruta_codigo,
            rutaDescrip = row.ruta_descrip,
            kePedStatus = row.ke_pedstatus
        )
    }

    val order: List<Order> = group.map { (key: Order, rows: List<FindOrders>) ->
        return@map Order(
            userId = key.userId,
            agencia = key.agencia,
            tipodoc = key.tipodoc,
            documento = key.documento,
            codcliente = key.codcliente,
            nombrecli = key.nombrecli,
            emision = key.emision,
            upickup = key.upickup,
            idcarrito = key.idcarrito,
            pickStartedAt = key.pickStartedAt,
            pickEndedAt = key.pickEndedAt,
            almacen = key.almacen,
            rutaCodigo = key.rutaCodigo,
            rutaDescrip = key.rutaDescrip,
            kePedStatus = key.kePedStatus,
            lines = rows.findOrdersToOrderLines()
        )
    }
    return order
}

fun List<FindOrders>.findOrdersToOrderLines(): List<OrderLine> {
    if (this.any { row -> row.documento_ == null }) return emptyList()

    val details: List<OrderLine> = this.map { row ->
        return@map OrderLine(
            userId = row.user_id_ ?: "",
            agencia = row.agencia_ ?: "",
            tipodoc = row.tipodoc_ ?: "",
            documento = row.documento_ ?: "",
            product = Product(
                codigo = row.codigo ?: "",
                nombre = row.nombre ?: "",
            ),
            almacen = row.almacen_ ?: "",
            cantref = row.cantref?.toInt() ?: 0,
            cantidad = row.cantidad?.toInt() ?: 0,
        )
    }
    return details
}

fun List<FindOrder>.findOrderToOrder(): Order? {
    val group: Map<Order, List<FindOrder>> = this.groupBy { row ->
        Order(
            userId = row.user_id,
            agencia = row.agencia,
            tipodoc = row.tipodoc,
            documento = row.documento,
            codcliente = row.codcliente,
            nombrecli = row.nombrecli,
            emision = row.emision,
            upickup = row.upickup,
            idcarrito = row.idcarrito,
            pickStartedAt = row.pickStartedAt,
            pickEndedAt = row.pickEndedAt,
            almacen = row.almacen,
            rutaCodigo = row.ruta_codigo,
            rutaDescrip = row.ruta_descrip,
            kePedStatus = row.ke_pedstatus
        )
    }

    val order: Order? = group.map { (key: Order, rows: List<FindOrder>) ->
        return@map Order(
            userId = key.userId,
            agencia = key.agencia,
            tipodoc = key.tipodoc,
            documento = key.documento,
            codcliente = key.codcliente,
            nombrecli = key.nombrecli,
            emision = key.emision,
            upickup = key.upickup,
            idcarrito = key.idcarrito,
            pickStartedAt = key.pickStartedAt,
            pickEndedAt = key.pickEndedAt,
            almacen = key.almacen,
            rutaCodigo = key.rutaCodigo,
            rutaDescrip = key.rutaDescrip,
            kePedStatus = key.kePedStatus,
            lines = rows.findOrderToOrderLines()
        )
    }.firstOrNull()
    return order
}

fun List<FindOrder>.findOrderToOrderLines(): List<OrderLine> {
    if (this.any { row -> row.documento_ == null }) return emptyList()

    val details: List<OrderLine> = this.map { row ->
        return@map OrderLine(
            userId = row.user_id_ ?: "",
            agencia = row.agencia_ ?: "",
            tipodoc = row.tipodoc_ ?: "",
            documento = row.documento_ ?: "",
            product = Product(
                agencia = row.agencia_ ?: "",
                codigo = row.codigo ?: "",
                grupo = row.grupo ?: "",
                subGrupo = row.subgrupo ?: "",
                nombre = row.nombre ?: "",
                referencia = row.referencia ?: "",
                marca = row.marca ?: "",
                unidad = row.unidad ?: "",
                existencia = row.existencia?.toInt() ?: 0,
                comprometido = row.comprometido?.toInt() ?: 0,
                image = row.images ?: "",
                createdAt = row.created_at ?: "",
            ),
            almacen = row.almacen_ ?: "",
            cantref = row.cantref?.toInt() ?: 0,
            cantidad = row.cantidad?.toInt() ?: 0,
        )
    }
    return details
}
