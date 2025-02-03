package org.closs.order.detail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.closs.core.presentation.shared.messages.Messages
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.unknown_error
import org.closs.core.types.shared.common.Constants.REFRESH_ORDER_KEY
import org.closs.core.types.shared.common.Constants.TOP_BAR_TITLE_KEY
import org.closs.core.types.shared.state.RequestState
import org.closs.core.types.shared.state.ResponseMessage
import org.closs.order.data.OrderRepository
import org.closs.order.detail.presentation.state.OrderDetailsState

class OrderDetailViewModel(
    private val id: String,
    private val repository: OrderRepository,
    val navigator: Navigator,
    private val messages: Messages,
    private val handle: SavedStateHandle,
) : ViewModel() {
    private val _state = MutableStateFlow(OrderDetailsState(isLoading = true))

    private val _reload = combine(
        _state,
        handle.getStateFlow(REFRESH_ORDER_KEY, true)
    ) { state, reload ->
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
                    messages.sendMessage(
                        ResponseMessage(
                            message = Res.string.unknown_error,
                            description = result.error
                        )
                    )
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
                messages.sendMessage(
                    ResponseMessage(
                        message = Res.string.unknown_error,
                        description = order.error
                    )
                )
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
}
