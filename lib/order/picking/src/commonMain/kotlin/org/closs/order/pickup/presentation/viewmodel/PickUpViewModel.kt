package org.closs.order.pickup.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.closs.core.presentation.shared.messages.Messages
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.unknown_error
import org.closs.core.types.shared.state.RequestState
import org.closs.core.types.shared.state.ResponseMessage
import org.closs.order.data.OrderRepository
import org.closs.order.pickup.presentation.events.PickUpEvents
import org.closs.order.pickup.presentation.state.PickUpState

class PickUpViewModel(
    private val id: String,
    private val repository: OrderRepository,
    val navigator: Navigator,
    private val messages: Messages,
) : ViewModel() {
    private val _order = repository.getOrder(id)

    private val _state = MutableStateFlow(PickUpState())
    val state = combine(
        _state,
        _order
    ) { state, order ->
        when (order) {
            is RequestState.Error -> {
                messages.sendMessage(
                    ResponseMessage(
                        message = Res.string.unknown_error,
                        description = "null"
                    )
                )
                navigator.navigateUp()
                state.copy(
                    loading = false,
                    order = null,
                )
            }
            is RequestState.Success -> {
                // todo: add filters here
                state.copy(
                    loading = false,
                    order = order.data,
                )
            }
            else -> {
                state.copy(
                    loading = true,
                    order = null,
                )
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        _state.value
    )

    fun onEvent(event: PickUpEvents) {
        when (event) {
            PickUpEvents.ToggleFilters -> TODO()
            PickUpEvents.ToggleCancelDialog -> TODO()
            PickUpEvents.OnCancelPickUp -> navigateBack()
            is PickUpEvents.OnQuantityChange -> TODO()
            is PickUpEvents.ToggleCheckProduct -> TODO()
            PickUpEvents.OnSubmitPickUp -> TODO()
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            navigator.navigateUp()
        }
    }
}
