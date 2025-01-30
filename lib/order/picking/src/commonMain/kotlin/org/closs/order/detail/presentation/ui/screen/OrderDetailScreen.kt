package org.closs.order.detail.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.closs.core.presentation.shared.ui.components.layout.loading.CircularLoadingComponent
import org.closs.core.presentation.shared.ui.components.navigation.BackHandlerComponent
import org.closs.order.detail.presentation.ui.components.OrderHeadComponent
import org.closs.order.detail.presentation.ui.components.OrderProductsListItem
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
    val state by viewModel.state.collectAsStateWithLifecycle()
    BackHandlerComponent(viewModel.navigator)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(6.dp)
    ) {
        when (state.isLoading) {
            true -> {
                item {
                    CircularLoadingComponent()
                }
            }
            false -> {
                val order = state.order
                if (order != null) {
                    item {
                        OrderHeadComponent(order)
                    }

                    items(order.lines) { line ->
                        OrderProductsListItem(line)
                    }
                } else {
                    item {

                    }
                }
            }
        }
    }
}
