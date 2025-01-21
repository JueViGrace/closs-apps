package org.closs.picking.home.di

import org.closs.home.shared.presentation.viewmodel.HomeViewModel
import org.closs.picking.home.presentation.viewmodel.DefaultHomeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun homeModule(): Module = module {
    viewModelOf(::DefaultHomeViewModel) bind HomeViewModel::class
}
