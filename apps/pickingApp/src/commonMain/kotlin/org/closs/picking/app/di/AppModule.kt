package org.closs.picking.app.di

import org.closs.shared.app.data.AppRepository
import org.closs.shared.app.presentation.viewmodel.AppViewModel
import org.closs.auth.di.authModule
import org.closs.order.detail.di.orderDetailModule
import org.closs.order.history.di.historyModule
import org.closs.order.orders.di.ordersModule
import org.closs.picking.app.data.DefaultAppRepository
import org.closs.picking.app.presentation.viewmodel.DefaultAppViewModel
import org.closs.picking.home.di.homeModule
import org.closs.product.di.productModule
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun appModule(): Module = module {
    singleOf(::DefaultAppRepository) bind AppRepository::class

    viewModelOf(::DefaultAppViewModel) bind AppViewModel::class

    includes(
        ktorModule(),
        databaseModule(),
        authModule(),
        homeModule(),
        ordersModule(),
        historyModule(),
        orderDetailModule(),
        productModule(),
    )
}
