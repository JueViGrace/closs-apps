package org.closs.picking.notifications.di

import org.closs.picking.notifications.data.DefaultNotificationsRepository
import org.closs.picking.notifications.presentation.viewmodel.DefaultNotificationsViewModel
import org.closs.shared.notifications.data.NotificationsRepository
import org.closs.shared.notifications.presentation.viewmodel.NotificationsViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun notificationsModule(): Module = module {
    singleOf(::DefaultNotificationsRepository) bind NotificationsRepository::class

    viewModelOf(::DefaultNotificationsViewModel) bind NotificationsViewModel::class
}
