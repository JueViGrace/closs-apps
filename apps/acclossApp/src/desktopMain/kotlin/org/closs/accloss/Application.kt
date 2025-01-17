package org.closs.accloss

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.closs.accloss.app.di.appModule
import org.closs.accloss.app.presentation.ui.screen.App
import org.closs.core.di.KoinBuilder
import org.closs.core.di.coreModule
import org.koin.dsl.koinApplication

fun main() = application {
    KoinBuilder(koinApplication())
        .addModule(coreModule())
        .addModule(appModule())
        .build()

    Window(
        onCloseRequest = ::exitApplication,
        title = "ACCLOSS",
    ) {
        App()
    }
}
