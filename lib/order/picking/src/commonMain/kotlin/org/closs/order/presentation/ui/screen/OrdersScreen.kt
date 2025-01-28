package org.closs.order.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.closs.core.presentation.shared.ui.components.layout.loading.LoadingComponent
import org.closs.core.presentation.shared.ui.components.navigation.BackHandlerComponent
import org.closs.core.types.shared.state.DisplayResult
import org.closs.order.presentation.ui.components.OrderListItem
import org.closs.order.presentation.viewmodel.OrdersViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OrdersScreen(
    viewModel: OrdersViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    BackHandlerComponent(viewModel.navigator)

    state.orders.DisplayResult(
        onLoading = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                LoadingComponent()
            }
        },
        onError = {},
        onSuccess = { orders ->
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(orders) { order ->
                    OrderListItem(
                        order = order,
                        onClick = {
                            viewModel.navigateToDetails(order.documento)
                        }
                    )
                }
            }
        }
    )
}
