package org.closs.product.di

import org.closs.product.data.repository.DefaultProductRepository
import org.closs.product.data.repository.ProductRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun productModule(): Module = module {
    singleOf(::DefaultProductRepository) bind ProductRepository::class
}
