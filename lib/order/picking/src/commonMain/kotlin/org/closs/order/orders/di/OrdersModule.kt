package org.closs.order.orders.di

import org.closs.order.orders.presentation.viewmodel.OrdersViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun ordersModule(): Module = module {
    viewModelOf(::OrdersViewModel)
}
