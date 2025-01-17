package org.closs.order.orders.presentation.ui.screen

import androidx.compose.runtime.Composable
import org.closs.core.presentation.shared.ui.components.navigation.BackHandlerComponent
import org.closs.order.orders.presentation.viewmodel.OrdersViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OrdersScreen(
    viewModel: OrdersViewModel = koinViewModel()
) {
    BackHandlerComponent(viewModel.navigator)
}
