package org.closs.order.di

import org.closs.order.data.DefaultOrderRepository
import org.closs.order.presentation.viewmodel.OrderDetailsViewModel
import org.closs.order.presentation.viewmodel.OrdersListViewModel
import org.closs.order.shared.data.OrderRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun orderModule(): Module = module {
    singleOf(::DefaultOrderRepository) bind OrderRepository::class

    viewModelOf(::OrdersListViewModel)

    viewModelOf(::OrderDetailsViewModel)
}
