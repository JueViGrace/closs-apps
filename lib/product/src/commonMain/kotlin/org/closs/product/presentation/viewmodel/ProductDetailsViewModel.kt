package org.closs.product.presentation.viewmodel

import androidx.lifecycle.ViewModel
import org.closs.product.presentation.state.ProductDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ProductDetailsViewModel : ViewModel() {
    private val _state = MutableStateFlow(ProductDetailsState())
    val state = _state.asStateFlow()
}
