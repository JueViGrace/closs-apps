package org.closs.order.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.closs.core.presentation.shared.ui.components.layout.loading.LinearLoadingComponent
import org.closs.core.presentation.shared.ui.components.navigation.BackHandlerComponent
import org.closs.order.presentation.ui.components.OrderListItem
import org.closs.order.presentation.viewmodel.OrdersViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OrdersScreen(
    viewModel: OrdersViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    BackHandlerComponent(viewModel.navigator)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state.isLoading) {
            true -> {
                item {
                    LinearLoadingComponent()
                }
            }
            false -> {
                // todo: sticky header for dates
                items(state.orders) { order ->
                    OrderListItem(
                        order = order,
                        onClick = {
                            viewModel.navigateToDetails(order.documento)
                        }
                    )
                }
            }
        }
    }
}
