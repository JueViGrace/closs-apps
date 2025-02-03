package org.closs.picking

import android.app.Application
import android.system.Os
import org.closs.core.di.KoinBuilder
import org.closs.core.di.coreModule
import org.closs.core.resources.R
import org.closs.picking.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.logger.Level
import org.koin.dsl.koinApplication

class PickingApp : Application() {
    override fun onCreate() {
        super.onCreate()

        Os.setenv("BASE_URL", getString(R.string.base_url), true)

        KoinBuilder(koinApplication())
            .addConfig(appDeclaration = {
                androidLogger(
                    level = if (BuildConfig.DEBUG) {
                        Level.DEBUG
                    } else {
                        Level.NONE
                    }
                )
                androidContext(this@PickingApp)
            })
            .addModule(modules = coreModule())
            .addModule(module = appModule())
            .build()
    }
}
