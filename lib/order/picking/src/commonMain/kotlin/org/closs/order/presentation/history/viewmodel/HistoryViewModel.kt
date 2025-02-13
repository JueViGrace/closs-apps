package org.closs.order.presentation.history.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.types.shared.common.Constants.REFRESH_ORDER_KEY
import org.closs.core.types.shared.state.RequestState
import org.closs.order.data.OrderRepository
import org.closs.order.presentation.history.events.HistoryEvents
import org.closs.order.presentation.history.state.HistoryState
import org.closs.order.presentation.orders.events.OrdersEvents

class HistoryViewModel(
    private val repository: OrderRepository,
    val navigator: Navigator,
    private val handle: SavedStateHandle,
) : ViewModel() {
    private val _orders = repository.getHistoryOrders()

    private val _state: MutableStateFlow<HistoryState> = MutableStateFlow(HistoryState())
    val state = combine(
        _state,
        _orders
    ) { state, orders ->
        when (orders) {
            is RequestState.Error -> {
                state.copy(
                    isLoading = false,
                    orders = emptyList()
                )
            }
            is RequestState.Success -> {
                state.copy(
                    isLoading = false,
                    orders = orders.data
                )
            }
            else -> {
                state.copy(
                    isLoading = true,
                    orders = emptyList()
                )
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        _state.value
    )

    fun onEvent(event: HistoryEvents) {
        when (event) {
            is HistoryEvents.NavigateToDetails -> navigateToDetails(event.id)
        }
    }

    private fun navigateToDetails(id: String) {
        // warn: this will trigger a refresh every time
        // consider changing refresh logic
        handle[REFRESH_ORDER_KEY] = true
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.OrderDetails(id),
                navOptions = NavOptions.Builder().apply {
                    setPopUpTo(
                        route = Destination.Orders,
                        inclusive = false,
                    )
                    setLaunchSingleTop(true)
                }.build()
            )
        }
    }
}
