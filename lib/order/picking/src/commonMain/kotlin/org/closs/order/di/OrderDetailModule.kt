package org.closs.order.di

import org.closs.order.presentation.detail.viewmodel.OrderDetailViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun orderDetailModule(): Module = module {
    viewModelOf(::OrderDetailViewModel)
}
