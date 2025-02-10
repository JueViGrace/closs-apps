package org.closs.order.presentation.pickup.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.closs.core.presentation.shared.navigation.ObserveAsEvents
import org.closs.core.presentation.shared.ui.components.buttons.BackArrowButton
import org.closs.core.presentation.shared.ui.components.icons.IconComponent
import org.closs.core.presentation.shared.ui.components.layout.bars.TopBarComponent
import org.closs.core.presentation.shared.ui.components.layout.loading.LinearLoadingComponent
import org.closs.core.presentation.shared.ui.components.navigation.BackHandlerComponent
import org.closs.core.presentation.shared.utils.calculateDefaultIconSize
import org.closs.core.presentation.shared.utils.calculateFABSize
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.ic_check
import org.closs.order.presentation.components.PickUpCancelDialog
import org.closs.order.presentation.components.PickUpListItem
import org.closs.order.presentation.components.SetIdCartDialog
import org.closs.order.presentation.pickup.events.PickUpEvents
import org.closs.order.presentation.pickup.viewmodel.PickUpViewModel
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickUpScreen(
    orderId: String,
    viewModel: PickUpViewModel = koinViewModel(
        parameters = { parametersOf(orderId) }
    )
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(
        flow = viewModel.messages.messages,
    ) { msg ->
        scope.launch {
            snackBarHostState.showSnackbar(
                "${msg.message?.let { getString(it) }} ${msg.description ?: ""}"
            )
        }
    }

    if (state.showCartDialog) {
        SetIdCartDialog(
            onDismiss = {
                viewModel.onEvent(PickUpEvents.Dismiss)
            },
            value = state.cartIdValue,
            onValueChange = { cartId ->
                viewModel.onEvent(PickUpEvents.OnCartChange(cartId))
            },
            errorMessage = state.cartIdError,
            isError = state.cartIdError != null,
            onSubmit = {
                viewModel.onEvent(PickUpEvents.OnSubmitCartId)
            }
        )
        BackHandlerComponent(
            navigator = viewModel.navigator,
            callBack = {
                viewModel.onEvent(PickUpEvents.Dismiss)
            }
        )
    } else {
        if (state.showCancelDialog) {
            PickUpCancelDialog(
                onCancel = {
                    viewModel.onEvent(PickUpEvents.ToggleCancelDialog)
                },
                onConfirm = {
                    viewModel.onEvent(PickUpEvents.Dismiss)
                }
            )
        }

        BackHandlerComponent(
            navigator = viewModel.navigator,
            callBack = {
                viewModel.onEvent(PickUpEvents.ToggleCancelDialog)
            }
        )
        Scaffold(
            topBar = {
                TopBarComponent(
                    navigationIcon = {
                        BackArrowButton {
                            viewModel.onEvent(PickUpEvents.ToggleCancelDialog)
                        }
                    },
                    actions = {
                        // todo: create filter menu
                    }
                )
            },
            snackbarHost = {
                SnackbarHost(
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(1f),
                    hostState = snackBarHostState
                )
            },
            floatingActionButton = {
                if (state.showFAB) {
                    FloatingActionButton(
                        modifier = Modifier.size(calculateFABSize()),
                        onClick = {
                            viewModel.onEvent(PickUpEvents.OnSubmitPickUp)
                        },
                    ) {
                        if (state.updateLoading) {
                            CircularProgressIndicator()
                        } else {
                            IconComponent(
                                modifier = Modifier.size(calculateDefaultIconSize()),
                                painter = painterResource(Res.drawable.ic_check),
                            )
                        }
                    }
                }
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier.padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally,
                contentPadding = PaddingValues(8.dp),
            ) {
                when (state.isLoading) {
                    true -> {
                        item {
                            LinearLoadingComponent()
                        }
                    }

                    false -> {
                        state.order?.let { order ->
                            item {
                            }

                            item {
                                HorizontalDivider()
                            }

                            itemsIndexed(
                                items = order.lines,
                                key = { key, line ->
                                    "$key${line.product.codigo}${line.userId}"
                                },
                                contentType = { _, line -> line.product }
                            ) { index, line ->
                                PickUpListItem(
                                    modifier = Modifier.fillMaxWidth(),
                                    line = line,
                                    value = state.quantityValue,
                                    errorMessage = state.quantityError,
                                    isError = state.quantityError != null,
                                    onQuantityChange = { quantity ->
                                        viewModel.onEvent(PickUpEvents.OnQuantityChange(index, quantity))
                                    },
                                    onCheckedChange = { checked ->
                                        viewModel.onEvent(PickUpEvents.ToggleCheckProduct(index, checked))
                                    },
                                )
                            }

                            item {
                                Spacer(
                                    modifier = Modifier.height(calculateFABSize() + 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
