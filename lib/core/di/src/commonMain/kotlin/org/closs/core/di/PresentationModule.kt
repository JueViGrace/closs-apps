package org.closs.core.di

import androidx.lifecycle.SavedStateHandle
import org.closs.core.presentation.shared.messages.DefaultMessages
import org.closs.core.presentation.shared.messages.Messages
import org.closs.core.presentation.shared.navigation.DefaultNavigator
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.Navigator
import org.koin.core.module.Module
import org.koin.dsl.module

fun presentationModule(): Module = module {
    single {
        SavedStateHandle()
    }

    single<Navigator> {
        DefaultNavigator(
            startDestination = Destination.Splash,
            stateHandle = get(),
        )
    }

    single<Messages> {
        DefaultMessages()
    }
}
