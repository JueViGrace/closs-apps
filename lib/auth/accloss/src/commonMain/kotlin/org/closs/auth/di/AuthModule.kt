package org.closs.auth.di

import org.closs.auth.data.DefaultAuthRepository
import org.closs.auth.presentation.viewmodel.AccountsListViewModel
import org.closs.auth.presentation.viewmodel.DefaultSignInViewModel
import org.closs.auth.shared.data.AuthRepository
import org.closs.auth.shared.presentation.viewmodel.ForgotPasswordViewModel
import org.closs.auth.shared.presentation.viewmodel.SignInViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun authModule(): Module = module {
    singleOf(::DefaultAuthRepository) bind AuthRepository::class

    viewModelOf(::DefaultSignInViewModel) bind SignInViewModel::class

    viewModelOf(::ForgotPasswordViewModel)

    viewModelOf(::AccountsListViewModel)
}
