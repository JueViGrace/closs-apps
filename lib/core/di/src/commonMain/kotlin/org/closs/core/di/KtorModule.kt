package org.closs.core.di

import org.closs.core.api.KtorClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun ktorModule(): Module = module {
    singleOf(::KtorClient)
}
