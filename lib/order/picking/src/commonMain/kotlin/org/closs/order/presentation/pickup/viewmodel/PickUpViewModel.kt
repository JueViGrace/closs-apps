package org.closs.order.presentation.pickup.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.tmapps.konnection.Konnection
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.closs.core.presentation.shared.messages.Messages
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.connection_unavailable
import org.closs.core.resources.resources.generated.resources.order_submitted
import org.closs.core.resources.resources.generated.resources.quantity_empty
import org.closs.core.resources.resources.generated.resources.quantity_exceeds_ordered
import org.closs.core.resources.resources.generated.resources.quantity_not_a_number
import org.closs.core.resources.resources.generated.resources.token_empty
import org.closs.core.resources.resources.generated.resources.token_in_use
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
    private val konnection: Konnection,
) : ViewModel() {
    private val _order = repository.getOrder(id)

    private val _state = MutableStateFlow(PickUpState(showCartDialog = true))
    val state = _state.asStateFlow()

    // todo: fix
    init {
        viewModelScope.launch {
            _order.collect { result ->
                when (result) {
                    is RequestState.Error -> {
                        messages.sendMessage(result.error)

                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                order = null,
                            )
                        }
                    }
                    is RequestState.Success -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = false,
                                order = result.data,
                            )
                        }
                    }
                    else -> {
                        _state.update { state ->
                            state.copy(
                                isLoading = true,
                                order = null,
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: PickUpEvents) {
        when (event) {
            PickUpEvents.Dismiss -> dismiss()
            is PickUpEvents.OnCartChange -> cartChange(event.id)
            PickUpEvents.OnSubmitCartId -> submitCartId()
            PickUpEvents.ToggleFilters -> toggleFilters()
            PickUpEvents.ToggleCancelDialog -> toggleCancelDialog()
            PickUpEvents.CancelPickUp -> cancelPickUp()
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
        _state.update { PickUpState() }

        navigateBack()
    }

    private fun cartChange(cartId: String) {
        when {
            cartId.isEmpty() -> {
                _state.update { state ->
                    state.copy(
                        cartIdValue = cartId,
                        cartIdError = Res.string.token_empty,
                        cartSubmitEnabled = false
                    )
                }
                return
            }
            else -> {
                _state.update { state ->
                    state.copy(
                        cartIdValue = cartId,
                        cartIdError = null,
                        cartSubmitEnabled = true
                    )
                }
            }
        }
    }

    private fun submitCartId() {
        if (!konnection.isConnected()) {
            viewModelScope.launch {
                messages.sendMessage(
                    ResponseMessage(
                        message = Res.string.connection_unavailable,
                    )
                )
            }
            return
        }

        _state.value.order?.let { order ->
            val updatedOrder = order.copy(idcarrito = _state.value.cartIdValue)
            viewModelScope.launch {
                repository.submitCartId(
                    order = updatedOrder
                ).collect { result ->
                    when (result) {
                        is RequestState.Error -> {
                            _state.update { state ->
                                state.copy(
                                    cartLoading = false,
                                    cartIdError = Res.string.token_in_use,
                                    cartSubmitEnabled = false,
                                )
                            }
                        }
                        is RequestState.Success -> {
                            _state.update { state ->
                                state.copy(
                                    cartLoading = false,
                                    cartIdError = null,
                                    cartSubmitEnabled = false,
                                    order = updatedOrder,
                                    showCartDialog = !state.showCartDialog
                                )
                            }
                        }
                        else -> {
                            _state.update { state ->
                                state.copy(
                                    cartLoading = true,
                                    cartIdError = null,
                                    cartSubmitEnabled = false,
                                )
                            }
                        }
                    }
                }
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

    private fun cancelPickUp() {
        _state.value.order?.let { order ->
            viewModelScope.launch {
                repository.submitCartId(order.copy(idcarrito = "")).collect { _ -> }
            }
        }

        dismiss()
    }

    private fun quantityChange(index: Int, quantity: String) {
        _state.value.order?.let { order ->
            when {
                quantity.isEmpty() -> {
                    _state.update { state ->
                        state.copy(
                            quantityValue = quantity,
                            quantityError = Res.string.quantity_empty,
                        )
                    }
                }
                quantity.any { !it.isDigit() } -> {
                    _state.update { state ->
                        state.copy(
                            quantityValue = "",
                            quantityError = Res.string.quantity_not_a_number,
                        )
                    }
                }
                quantity.toInt() > order.lines[index].cantref -> {
                    _state.update { state ->
                        state.copy(
                            quantityValue = quantity,
                            quantityError = Res.string.quantity_exceeds_ordered
                        )
                    }
                }
                else -> {
                    val mutableLines = order.lines.toMutableList()
                    val updatedLine = order.lines[index].copy(cantidad = quantity.toInt())
                    mutableLines[index] = updatedLine
                    _state.update { state ->
                        state.copy(
                            order = order.copy(
                                lines = mutableLines.toList()
                            ),
                            quantityValue = quantity,
                            quantityError = null
                        )
                    }
                }
            }
        }
    }

    private fun checkProduct(index: Int, checked: Boolean) {
        if (_state.value.quantityValue.isEmpty()) {
            _state.update { state ->
                state.copy(
                    quantityValue = "",
                    quantityError = Res.string.quantity_empty,
                )
            }
            return
        }

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
        if (!konnection.isConnected()) {
            viewModelScope.launch {
                messages.sendMessage(
                    ResponseMessage(
                        message = Res.string.connection_unavailable,
                    )
                )
            }
            return
        }
        _state.value.order?.let { order ->
            viewModelScope.launch {
                repository.submitOrder(
                    order = order
                ).collect { result ->
                    when (result) {
                        is RequestState.Error -> {
                            messages.sendMessage(result.error)
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

                            delay(500)
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
