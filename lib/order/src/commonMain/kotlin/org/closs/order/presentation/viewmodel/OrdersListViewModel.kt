package org.closs.order.presentation.viewmodel

import androidx.lifecycle.ViewModel
import org.closs.order.presentation.state.OrdersListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrdersListViewModel : ViewModel() {
    private val _state = MutableStateFlow(OrdersListState())
    val state = _state.asStateFlow()
}
