package org.closs.accloss.settings.di

import org.closs.accloss.settings.data.DefaultSettingsRepository
import org.closs.accloss.settings.presentation.viewmodel.DefaultSettingsViewModel
import org.closs.shared.settings.data.SettingsRepository
import org.closs.shared.settings.presentation.viewmodel.SettingsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun settingsModule(): Module = module {
    singleOf(::DefaultSettingsRepository) bind SettingsRepository::class

    viewModelOf(::DefaultSettingsViewModel) bind SettingsViewModel::class
}
