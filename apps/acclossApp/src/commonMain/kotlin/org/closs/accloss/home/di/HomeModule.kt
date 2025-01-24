package org.closs.accloss.home.di

import org.closs.accloss.home.data.DefaultHomeRepository
import org.closs.accloss.home.presentation.viewmodel.DefaultHomeViewModel
import org.closs.shared.home.data.HomeRepository
import org.closs.shared.home.presentation.viewmodel.HomeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun homeModule(): Module = module {
    singleOf(::DefaultHomeRepository) bind HomeRepository::class

    viewModelOf(::DefaultHomeViewModel) bind HomeViewModel::class
}
