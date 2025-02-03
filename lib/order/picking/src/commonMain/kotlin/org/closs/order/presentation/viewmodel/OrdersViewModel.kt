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
import org.closs.core.types.shared.common.Constants.TOP_BAR_TITLE_KEY
import org.closs.core.types.shared.state.RequestState
import org.closs.core.types.shared.state.ResponseMessage
import org.closs.order.data.OrderRepository
import org.closs.order.presentation.state.OrdersState

class OrdersViewModel(
    private val repository: OrderRepository,
    val navigator: Navigator,
    private val messages: Messages,
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

    fun navigateToDetails(orderId: String) {
        handle[TOP_BAR_TITLE_KEY] = ""
        handle[REFRESH_ORDER_KEY] = true
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.OrderDetails(orderId),
                navOptions = NavOptions.Builder().apply {
                    setPopUpTo(Destination.Orders, false)
                    setLaunchSingleTop(true)
                }.build()
            )
        }
    }
}
