package org.closs.accloss.app.di

import org.closs.core.api.client.DefaultKtorClient
import org.closs.core.api.shared.client.KtorClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun ktorModule(): Module = module {
    singleOf(::DefaultKtorClient) bind KtorClient::class
}
