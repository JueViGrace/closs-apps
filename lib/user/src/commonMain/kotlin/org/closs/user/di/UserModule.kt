package org.closs.user.di

import org.closs.user.data.repository.DefaultUserRepository
import org.closs.user.data.repository.UserRepository
import org.closs.user.presentation.viewmodel.UserDetailsViewModel
import org.closs.user.presentation.viewmodel.UsersListViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun userModule(): Module = module {
    singleOf(::DefaultUserRepository) bind UserRepository::class

    viewModelOf(::UsersListViewModel)

    viewModelOf(::UserDetailsViewModel)
}
