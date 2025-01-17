package org.closs.picking.home.di

import org.closs.picking.home.presentation.viewmodel.HomeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun homeModule(): Module = module {
    viewModelOf(::HomeViewModel)
}
