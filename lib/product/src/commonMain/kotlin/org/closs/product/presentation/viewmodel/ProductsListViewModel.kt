package org.closs.product.presentation.viewmodel

import androidx.lifecycle.ViewModel
import org.closs.product.presentation.state.ProductsListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductsListViewModel : ViewModel() {
    private val _state = MutableStateFlow(ProductsListState())
    val state = _state.asStateFlow()
}
