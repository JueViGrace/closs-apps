package org.closs.core.di

import dev.tmapps.konnection.Konnection
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.time.Duration.Companion.seconds

actual fun connectionModule(): Module = module {
    single {
        Konnection.createInstance(
            enableDebugLog = true,
            connectionCheckTime = 5.seconds,
            pingHostCheckers = listOf("cloccidental.com"),
        )
    }
}
