package org.closs.core.di

import dev.tmapps.konnection.Konnection
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun connectionModule(): Module = module {
    single {
        Konnection.createInstance(
            context = get(),
            enableDebugLog = true,
        )
    }
}
