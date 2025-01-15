package org.closs.core.di

import org.koin.core.module.Module

fun coreModule(): List<Module> = listOf(
    coroutinesModule(),
    presentationModule(),
)
