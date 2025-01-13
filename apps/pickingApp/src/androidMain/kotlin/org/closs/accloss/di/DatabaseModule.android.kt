package org.closs.accloss.di

import org.closs.accloss.core.database.ACCLOSSDbHelper
import org.closs.core.database.driver.ACCLOSSDriver
import org.closs.core.database.driver.DriverFactory
import org.closs.core.database.helper.DbHelper
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun databaseModule(): Module = module {
    singleOf(::ACCLOSSDriver) bind DriverFactory::class

    singleOf(::ACCLOSSDbHelper) bind DbHelper::class
}
