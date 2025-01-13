package org.closs.picking.di

import org.closs.core.database.driver.DriverFactory
import org.closs.core.database.driver.PickingDriver
import org.closs.core.database.helper.PickingDbHelper
import org.closs.core.database.helper.PickingDbHelperImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun databaseModule(): Module = module {
    singleOf(::PickingDriver) bind DriverFactory::class

    singleOf(::PickingDbHelperImpl) bind PickingDbHelper::class
}
