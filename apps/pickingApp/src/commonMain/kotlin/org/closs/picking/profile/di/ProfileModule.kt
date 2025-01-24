package org.closs.picking.profile.di

import org.closs.picking.profile.data.DefaultProfileRepository
import org.closs.picking.profile.presentation.viewmodel.DefaultProfileViewModel
import org.closs.shared.profile.data.ProfileRepository
import org.closs.shared.profile.presentation.viewmodel.ProfileViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun profileModule(): Module = module {
    singleOf(::DefaultProfileRepository) bind ProfileRepository::class

    viewModelOf(::DefaultProfileViewModel) bind ProfileViewModel::class
}
