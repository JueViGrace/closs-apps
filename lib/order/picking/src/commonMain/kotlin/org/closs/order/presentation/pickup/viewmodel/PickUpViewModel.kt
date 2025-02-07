package org.closs.order.presentation.pickup.viewmodel

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
import org.closs.core.resources.resources.generated.resources.order_submitted
import org.closs.core.resources.resources.generated.resources.token_empty
import org.closs.core.resources.resources.generated.resources.unexpected_error
import org.closs.core.resources.resources.generated.resources.unknown_error
import org.closs.core.types.shared.common.Constants
import org.closs.core.types.shared.state.RequestState
import org.closs.core.types.shared.state.ResponseMessage
import org.closs.order.data.OrderRepository
import org.closs.order.presentation.pickup.events.PickUpEvents
import org.closs.order.presentation.pickup.state.PickUpState

class PickUpViewModel(
    id: String,
    private val repository: OrderRepository,
    val navigator: Navigator,
    val messages: Messages,
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
                    isLoading = false,
                    dbOrder = null,
                )
            }
            is RequestState.Success -> {
                // todo: add filters here
                state.copy(
                    isLoading = false,
                    dbOrder = order.data,
                )
            }
            else -> {
                state.copy(
                    isLoading = true,
                    dbOrder = null,
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
            is PickUpEvents.OnQuantityChange -> quantityChange(event.index, event.quantity)
            is PickUpEvents.ToggleCheckProduct -> checkProduct(event.index, event.checked)
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
        _state.update {
            PickUpState(
                showCartDialog = false
            )
        }

        navigateBack()
    }

    private fun cartChange(cartId: String) {
        when {
            cartId.isEmpty() -> {
                return _state.update { state ->
                    state.copy(
                        cartId = cartId,
                        cartIdError = Res.string.token_empty,
                        submitCartEnabled = false
                    )
                }
            }
            else -> {
                _state.update { state ->
                    state.copy(
                        cartId = cartId,
                        cartIdError = null,
                        submitCartEnabled = true
                    )
                }
            }
        }
    }

    private fun submitCartId() {
        state.value.dbOrder?.let { order ->
            _state.update { state ->
                state.copy(
                    order = order.copy(
                        idcarrito = state.cartId,
                        pickStartedAt = Constants.currentTime
                    ),
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

    private fun quantityChange(index: Int, quantity: String) {
        _state.value.order?.let { order ->
            val mutableLines = order.lines.toMutableList()
            when {
                quantity.isEmpty() -> {
                    val updatedLine = order.lines[index].copy(cantidad = 0)
                    mutableLines[index] = updatedLine
                    _state.update { state ->
                        state.copy(
                            order = order.copy(
                                lines = mutableLines.toList()
                            ),
                        )
                    }
                }
                else -> {
                    val updatedLine = order.lines[index].copy(cantidad = quantity.toInt())
                    mutableLines[index] = updatedLine
                    _state.update { state ->
                        state.copy(
                            order = order.copy(
                                lines = mutableLines.toList()
                            ),
                        )
                    }
                }
            }
        }
    }

    private fun checkProduct(index: Int, checked: Boolean) {
        _state.value.order?.let { order ->
            val mutableLines = order.lines.toMutableList()
            val updatedLine = order.lines[index].copy(checked = checked)
            mutableLines[index] = updatedLine
            _state.update { state ->
                state.copy(
                    order = order.copy(
                        lines = mutableLines.toList(),
                    ),
                )
            }
        }
        _state.update { state ->
            state.copy(
                showFAB = _state.value.order?.lines?.all { it.checked } ?: false
            )
        }
    }

    private fun submitPickUp() {
        _state.value.order?.let { order ->
            viewModelScope.launch {
                println(Constants.currentTime)
                repository.submitOrder(
                    order = order.copy(pickEndedAt = Constants.currentTime)
                ).collect { result ->
                    when (result) {
                        is RequestState.Error -> {
                            messages.sendMessage(
                                ResponseMessage(
                                    message = Res.string.unexpected_error,
                                    description = result.error
                                )
                            )

                            _state.update { state ->
                                state.copy(
                                    updateLoading = false,
                                )
                            }
                        }
                        is RequestState.Success -> {
                            messages.sendMessage(
                                ResponseMessage(
                                    message = Res.string.order_submitted,
                                )
                            )

                            // todo: fix navigation
                            dismiss()
                        }
                        else -> {
                            _state.update { state ->
                                state.copy(
                                    updateLoading = true,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
