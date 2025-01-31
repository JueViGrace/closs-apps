package org.closs.order.detail.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import org.closs.core.presentation.shared.messages.Messages
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.unknown_error
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
    private val _order = repository.getOrder(id)

    private val _state = MutableStateFlow(OrderDetailsState())
    val state = combine(
        _state,
        _order
    ) { state, order ->
        when (order) {
            is RequestState.Error -> {
                messages.sendMessage(
                    ResponseMessage(
                        message = Res.string.unknown_error,
                        description = order.error
                    )
                )
                state.copy(
                    order = null,
                    isLoading = false
                )
            }
            is RequestState.Success -> {
                handle[TOP_BAR_TITLE_KEY] = order.data?.documento ?: ""
                state.copy(
                    order = order.data,
                    isLoading = false
                )
            }
            else -> {
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
