package org.closs.order.detail.di

import org.closs.order.detail.presentation.viewmodel.OrderDetailViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun orderDetailModule(): Module = module {
    viewModelOf(::OrderDetailViewModel)
}
