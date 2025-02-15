package org.closs.order.presentation.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import dev.tmapps.konnection.Konnection
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.closs.core.presentation.shared.actions.FABActions
import org.closs.core.presentation.shared.messages.Messages
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.types.shared.common.Constants.REFRESH_ORDER_KEY
import org.closs.core.types.shared.state.RequestState
import org.closs.order.data.OrderRepository
import org.closs.order.presentation.detail.events.OrderDetailEvents
import org.closs.order.presentation.detail.state.OrderDetailsState

class OrderDetailViewModel(
    private val id: String,
    private val repository: OrderRepository,
    val navigator: Navigator,
    val messages: Messages,
    private val handle: SavedStateHandle,
    private val konnection: Konnection,
) : ViewModel() {
    private val _state = MutableStateFlow(OrderDetailsState(isLoading = true))

    private val _reload = combine(
        _state,
        handle.getStateFlow(REFRESH_ORDER_KEY, true)
    ) { state, reload ->
        if (!konnection.isConnected()) {
            return@combine state.copy(
                isLoading = false
            )
        }
        state.copy(
            isLoading = reload
        )
    }

    private val _order = repository.getOrder(id)

    private val _reloadOrders = combine(
        _reload,
        _order,
    ) { reload, order ->
        if (reload.isLoading) {
            repository.fetchOrder(id).collect { result ->
                if (result is RequestState.Error) {
                    messages.sendMessage(result.error)
                }
            }
        }
        order
    }

    val state = combine(
        _state,
        _reloadOrders
    ) { state, order ->
        when (order) {
            is RequestState.Error -> {
                messages.sendMessage(order.error)
                handle[REFRESH_ORDER_KEY] = false
                state.copy(
                    order = null,
                    isLoading = false
                )
            }
            is RequestState.Success -> {
                handle[REFRESH_ORDER_KEY] = false
                state.copy(
                    order = order.data,
                    isLoading = false
                )
            }
            else -> {
                handle[REFRESH_ORDER_KEY] = false
                state.copy(
                    order = null,
                    isLoading = true
                )
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _state.value
    )

    fun onEvent(event: OrderDetailEvents) {
        when (event) {
            OrderDetailEvents.Refresh -> refresh()
            OrderDetailEvents.ToggleFABMenu -> toggleFABMenu()
            is OrderDetailEvents.OnFABActionClick -> handleFABActions(event.action)
        }
    }

    private fun refresh() {
        handle[REFRESH_ORDER_KEY] = true
    }

    private fun toggleFABMenu() {
        _state.update { state ->
            state.copy(
                showFABMenu = !state.showFABMenu
            )
        }
    }

    private fun handleFABActions(action: FABActions) {
        toggleFABMenu()
        when (action) {
            FABActions.PickUp -> navigateToPickUp()
        }
    }

    private fun navigateToPickUp() {
        viewModelScope.launch {
            delay(100)
            navigator.navigate(
                destination = Destination.PickUp(id),
                navOptions = NavOptions.Builder().apply {
                    setPopUpTo(
                        route = Destination.OrderDetails(id),
                        inclusive = false,
                        saveState = true
                    )
                    setLaunchSingleTop(true)
                    setRestoreState(true)
                }.build()
            )
        }
    }
}
