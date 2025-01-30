package org.closs.order.pickup.presentation.ui.screen

import androidx.compose.runtime.Composable
import org.closs.core.presentation.shared.ui.components.navigation.BackHandlerComponent
import org.closs.order.pickup.presentation.viewmodel.PickUpViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun PickUpScreen(
    orderId: String,
    viewModel: PickUpViewModel = koinViewModel(
        parameters = { parametersOf(orderId) }
    )
) {
    BackHandlerComponent(viewModel.navigator)
}
