package org.closs.order.detail.presentation.ui.screen

import androidx.compose.runtime.Composable
import org.closs.core.presentation.shared.ui.components.navigation.BackHandlerComponent
import org.closs.order.detail.presentation.viewmodel.OrderDetailViewModel
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun OrderDetailScreen(
    orderId: String,
    viewModel: OrderDetailViewModel = koinViewModel(
        parameters = { parametersOf(orderId) }
    )
) {
    BackHandlerComponent(viewModel.navigator)
}
