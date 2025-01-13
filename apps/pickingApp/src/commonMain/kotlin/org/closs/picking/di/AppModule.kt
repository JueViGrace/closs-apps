package org.closs.picking.di

import org.koin.core.module.Module
import org.koin.dsl.module

fun appModule(): Module = module {
    includes(
        databaseModule()
    )
}
