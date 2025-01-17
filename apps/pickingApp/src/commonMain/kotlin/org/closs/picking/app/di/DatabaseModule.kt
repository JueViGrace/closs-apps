package org.closs.picking.app.di

import org.closs.core.database.driver.DefaultDriverFactory
import org.closs.core.database.helper.DefaultDbHelper
import org.closs.core.database.helper.PickingDbHelper
import org.closs.core.database.shared.driver.DriverFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun databaseModule(): Module = module {
    singleOf(::DefaultDriverFactory) bind DriverFactory::class

    singleOf(::DefaultDbHelper) bind PickingDbHelper::class
}
