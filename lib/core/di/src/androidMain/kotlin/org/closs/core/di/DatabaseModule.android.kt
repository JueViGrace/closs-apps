package org.closs.core.di

import org.closs.core.database.driver.DriverFactory
import org.closs.core.database.helper.DbHelper
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual fun databaseModule(): Module = module {
    singleOf(::DriverFactory)

    singleOf(::DbHelper)
}
