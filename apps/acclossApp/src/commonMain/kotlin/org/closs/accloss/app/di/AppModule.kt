package org.closs.accloss.app.di

import org.closs.accloss.app.data.DefaultAppRepository
import org.closs.accloss.app.presentation.viewmodel.DefaultAppViewModel
import org.closs.accloss.home.di.homeModule
import org.closs.app.shared.data.AppRepository
import org.closs.app.shared.presentation.viewmodel.AppViewModel
import org.closs.auth.di.authModule
import org.closs.order.di.orderModule
import org.closs.product.di.productModule
import org.closs.user.di.userModule
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
        userModule(),
        productModule(),
        orderModule(),
    )
}
