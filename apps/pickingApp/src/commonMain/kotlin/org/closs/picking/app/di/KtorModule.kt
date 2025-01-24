package org.closs.picking.app.di

import org.closs.core.api.client.DefaultKtorClient
import org.closs.core.api.shared.auth.AuthClient
import org.closs.core.api.shared.client.KtorClient
import org.closs.core.api.user.UserClient
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun ktorModule(): Module = module {
    singleOf(::DefaultKtorClient) bind KtorClient::class

    singleOf(::AuthClient)

    singleOf(::UserClient)
}
