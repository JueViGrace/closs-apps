package org.closs.accloss.app.di

import org.closs.core.database.driver.DefaultDriverFactory
import org.closs.core.database.helper.ACCLOSSDbHelper
import org.closs.core.database.helper.DefaultDbHelper
import org.closs.core.database.shared.driver.DriverFactory
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

actual fun databaseModule(): Module = module {
    singleOf(::DefaultDriverFactory) bind DriverFactory::class

    singleOf(::DefaultDbHelper) bind ACCLOSSDbHelper::class
}
