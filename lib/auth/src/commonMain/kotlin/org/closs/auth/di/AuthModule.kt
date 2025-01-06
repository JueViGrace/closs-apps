package org.closs.auth.di

import org.closs.auth.data.repository.AuthRepository
import org.closs.auth.data.repository.DefaultAuthRepository
import org.closs.auth.presentation.viewmodel.AccountsListViewModel
import org.closs.auth.presentation.viewmodel.ForgotPasswordViewModel
import org.closs.auth.presentation.viewmodel.SignInViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun authModule(): Module = module {
    singleOf(::DefaultAuthRepository) bind AuthRepository::class

    viewModelOf(::SignInViewModel)

    viewModelOf(::ForgotPasswordViewModel)

    viewModelOf(::AccountsListViewModel)
}
