package org.closs.order.pickup.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.closs.core.presentation.shared.messages.Messages
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.token_empty
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
            PickUpEvents.Dismiss -> dismiss()
            is PickUpEvents.OnCartChange -> cartChange(event.id)
            PickUpEvents.OnSubmitCartId -> submitCartId()
            PickUpEvents.ToggleFilters -> toggleFilters()
            PickUpEvents.ToggleCancelDialog -> toggleCancelDialog()
            is PickUpEvents.OnQuantityChange -> quantityChange(event.quantity)
            is PickUpEvents.ToggleCheckProduct -> checkProduct(event.index)
            PickUpEvents.OnSubmitPickUp -> submitPickUp()
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            delay(100)
            navigator.navigateUp()
        }
    }

    private fun dismiss() {
        _state.update { state ->
            state.copy(
                order = null,
                loading = false,
                cartId = "",
                showCartDialog = false,
                cartIdError = null,
                showFilters = false,
                showFAB = false,
                showCancelDialog = false,
            )
        }

        navigateBack()
    }

    private fun cartChange(id: String) {
        when {
            id.isEmpty() -> {
                return _state.update { state ->
                    state.copy(
                        cartId = id,
                        cartIdError = Res.string.token_empty,
                        submitCartEnabled = false
                    )
                }
            }
            else -> {
                _state.update { state ->
                    state.copy(
                        cartId = id,
                        cartIdError = null,
                        submitCartEnabled = true
                    )
                }
            }
        }
    }

    private fun submitCartId() {
        state.value.order?.let { order ->
            viewModelScope.launch {
                repository.updateOrderCart(
                    order = order.copy(
                        idcarrito = state.value.cartId
                    )
                )
            }
            _state.update { state ->
                state.copy(
                    showCartDialog = !state.showCartDialog
                )
            }
        }
    }

    private fun toggleFilters() {
        _state.update { state ->
            state.copy(
                showFilters = !state.showFilters
            )
        }
    }

    private fun toggleCancelDialog() {
        _state.update { state ->
            state.copy(
                showCancelDialog = !state.showCancelDialog
            )
        }
    }

    private fun quantityChange(quantity: Int) {
    }

    private fun checkProduct(index: Int) {
    }

    private fun submitPickUp() {
    }
}
