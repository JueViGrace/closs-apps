package org.closs.accloss

import android.app.Application
import org.closs.accloss.di.appModule
import org.closs.core.di.KoinBuilder
import org.closs.core.di.coreModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.koin.dsl.koinApplication

class ACCLOSSApp : Application() {
    override fun onCreate() {
        super.onCreate()

        KoinBuilder(koinApplication())
            .addConfig(appDeclaration = {
                androidLogger(
                    level = if (BuildConfig.DEBUG) {
                        Level.DEBUG
                    } else {
                        Level.NONE
                    }
                )
                androidContext(this@ACCLOSSApp)
            })
            .addModule(modules = coreModule())
            .addModule(module = appModule())
            .build()
    }
}
