package org.closs.order.di

import org.closs.order.data.DefaultOrderRepository
import org.closs.order.data.OrderRepository
import org.closs.order.detail.di.orderDetailModule
import org.closs.order.history.di.historyModule
import org.closs.order.pickup.di.pickUpModule
import org.closs.order.presentation.viewmodel.OrdersViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun ordersModule(): Module = module {
    singleOf(::DefaultOrderRepository) bind OrderRepository::class

    viewModelOf(::OrdersViewModel)

    includes(
        orderDetailModule(),
        historyModule(),
        pickUpModule(),
    )
}
