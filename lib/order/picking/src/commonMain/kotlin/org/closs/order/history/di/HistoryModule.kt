package org.closs.order.history.di

import org.closs.order.history.presentation.viewmodel.HistoryViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun historyModule(): Module = module {
    viewModelOf(::HistoryViewModel)
}
