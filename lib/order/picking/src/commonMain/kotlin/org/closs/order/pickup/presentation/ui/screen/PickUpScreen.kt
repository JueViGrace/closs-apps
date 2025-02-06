package org.closs.order.pickup.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.closs.core.presentation.shared.ui.components.buttons.BackArrowButton
import org.closs.core.presentation.shared.ui.components.icons.IconComponent
import org.closs.core.presentation.shared.ui.components.layout.bars.TopBarComponent
import org.closs.core.presentation.shared.ui.components.navigation.BackHandlerComponent
import org.closs.core.presentation.shared.utils.calculateDefaultIconSize
import org.closs.core.presentation.shared.utils.calculateFABSize
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.ic_check
import org.closs.order.pickup.presentation.events.PickUpEvents
import org.closs.order.pickup.presentation.ui.components.PickUpCancelDialog
import org.closs.order.pickup.presentation.ui.components.SetIdCartDialog
import org.closs.order.pickup.presentation.viewmodel.PickUpViewModel
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

    if (state.showCartDialog) {
        SetIdCartDialog(
            onDismiss = {
                viewModel.onEvent(PickUpEvents.Dismiss)
            },
            value = state.cartId,
            onValueChange = { cartId ->
                viewModel.onEvent(PickUpEvents.OnCartChange(cartId))
            },
            errorMessage = state.cartIdError,
            error = state.cartIdError != null,
            enabled = state.submitCartEnabled,
            onSubmit = {
                viewModel.onEvent(PickUpEvents.OnSubmitCartId)
            }
        )
    }

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
        callBack = if (state.showCartDialog) {
            {
                viewModel.onEvent(PickUpEvents.Dismiss)
            }
        } else {
            {
                viewModel.onEvent(PickUpEvents.ToggleCancelDialog)
            }
        }
    )
    Scaffold(
        topBar = {
            TopBarComponent(
                // todo: create and show dialog when clicked
                navigationIcon = {
                    BackArrowButton {
                        viewModel.onEvent(PickUpEvents.ToggleCancelDialog)
                    }
                }
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
                    IconComponent(
                        modifier = Modifier.size(calculateDefaultIconSize()),
                        painter = painterResource(Res.drawable.ic_check),
                    )
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
        }
    }
}
