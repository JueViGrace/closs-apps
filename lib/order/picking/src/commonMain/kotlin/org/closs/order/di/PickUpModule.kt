package org.closs.order.di

import org.closs.order.presentation.pickup.viewmodel.PickUpViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun pickUpModule(): Module = module {
    viewModelOf(::PickUpViewModel)
}