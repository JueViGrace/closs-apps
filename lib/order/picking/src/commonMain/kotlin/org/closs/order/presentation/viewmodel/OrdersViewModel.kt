package org.closs.order.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.closs.core.presentation.shared.messages.Messages
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.unknown_error
import org.closs.core.types.shared.common.Constants.REFRESH_ORDERS_KEY
import org.closs.core.types.shared.common.Constants.REFRESH_ORDER_KEY
import org.closs.core.types.shared.state.RequestState
import org.closs.core.types.shared.state.ResponseMessage
import org.closs.order.data.OrderRepository
import org.closs.order.presentation.events.OrdersEvents
import org.closs.order.presentation.state.OrdersState

class OrdersViewModel(
    private val repository: OrderRepository,
    val navigator: Navigator,
    val messages: Messages,
    private val handle: SavedStateHandle,
) : ViewModel() {
    private val _state = MutableStateFlow(OrdersState(isLoading = true))

    private val _reload = combine(
        _state,
        handle.getStateFlow(REFRESH_ORDERS_KEY, true)
    ) { state, reload ->
        state.copy(
            isLoading = reload
        )
    }
    private val _orders = repository.getOrders()

    private val _reloadOrders = combine(
        _reload,
        _orders
    ) { reload, orders ->
        if (reload.isLoading) {
            repository.fetchOrders().collect { result ->
                if (result is RequestState.Error) {
                    messages.sendMessage(
                        ResponseMessage(
                            message = Res.string.unknown_error,
                            description = result.error
                        )
                    )
                }
            }
        }
        orders
    }

    val state = combine(
        _state,
        _reloadOrders,
    ) { state, orders ->
        when (orders) {
            is RequestState.Error -> {
                messages.sendMessage(
                    ResponseMessage(
                        message = Res.string.unknown_error,
                        description = orders.error
                    )
                )
                handle[REFRESH_ORDERS_KEY] = false
                state.copy(
                    isLoading = false
                )
            }

            is RequestState.Success -> {
                handle[REFRESH_ORDERS_KEY] = false
                state.copy(
                    orders = orders.data,
                    isLoading = false
                )
            }

            else -> {
                handle[REFRESH_ORDERS_KEY] = false
                state.copy(
                    orders = emptyList(),
                    isLoading = true
                )
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _state.value
    )

    fun onEvent(event: OrdersEvents) {
        when (event) {
            OrdersEvents.Refresh -> refresh()
            is OrdersEvents.NavigateToDetails -> navigateToDetails(event.id)
        }
    }

    private fun refresh() {
        handle[REFRESH_ORDERS_KEY] = true
    }

    private fun navigateToDetails(id: String) {
        // warn: this will trigger a refresh every time
        // consider changing refresh logic
        handle[REFRESH_ORDER_KEY] = true
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.OrderDetails(id),
                navOptions = NavOptions.Builder().apply {
                    setPopUpTo(Destination.Orders, false)
                    setLaunchSingleTop(true)
                }.build()
            )
        }
    }
}
