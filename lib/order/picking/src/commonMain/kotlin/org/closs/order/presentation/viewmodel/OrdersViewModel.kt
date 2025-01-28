package org.closs.order.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.closs.core.presentation.shared.messages.Messages
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.types.shared.common.Constants.REFRESH_ORDERS_KEY
import org.closs.core.types.shared.state.RequestState
import org.closs.order.data.OrderRepository
import org.closs.order.presentation.state.OrdersState

class OrdersViewModel(
    private val repository: OrderRepository,
    val navigator: Navigator,
    private val messages: Messages,
    private val handle: SavedStateHandle,
) : ViewModel() {
    private val _reloadOrders: StateFlow<Boolean> = handle.getStateFlow(REFRESH_ORDERS_KEY, true)
    private var _orders = repository.getOrders(_reloadOrders.value)

    private val _state = MutableStateFlow(OrdersState())
    val state = combine(
        _state,
        _orders,
        _reloadOrders
    ) { state, orders, reload ->
        if (reload) {
            refreshOrders()
        }
        state.copy(
            orders = orders
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _state.value
    )

    fun navigateToDetails(orderId: String) {
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.OrderDetails(orderId),
                navOptions = NavOptions.Builder().apply {
                    setPopUpTo(inclusive = false, route = Destination.Orders, saveState = true)
                }.build()
            )
        }
    }

    private fun refreshOrders() {
        _state.update { state ->
            state.copy(
                orders = RequestState.Loading
            )
        }
        viewModelScope.launch {
            repository.getOrders(true).collect { result ->
                _state.update { state ->
                    state.copy(
                        orders = result
                    )
                }
            }
        }
        handle[REFRESH_ORDERS_KEY] = false
    }
}
