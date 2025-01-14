package org.closs.product.presentation.state

import org.closs.core.types.shared.product.Product

data class ProductsListState(
    val products: List<Product> = emptyList(),
)
