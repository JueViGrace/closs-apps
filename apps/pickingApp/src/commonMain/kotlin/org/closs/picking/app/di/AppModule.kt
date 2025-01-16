package org.closs.picking.di

import org.closs.app.shared.data.AppRepository
import org.closs.app.shared.presentation.viewmodel.AppViewModel
import org.closs.auth.di.authModule
import org.closs.order.di.orderModule
import org.closs.picking.data.DefaultAppRepository
import org.closs.picking.presentation.viewmodel.DefaultAppViewModel
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
        productModule(),
        orderModule(),
    )
}
