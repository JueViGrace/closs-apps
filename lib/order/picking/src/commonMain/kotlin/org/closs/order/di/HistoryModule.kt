package org.closs.order.di

import org.closs.order.presentation.history.viewmodel.HistoryViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun historyModule(): Module = module {
    viewModelOf(::HistoryViewModel)
}
