package org.closs.order.detail.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.closs.core.presentation.shared.ui.components.layout.loading.CircularLoadingComponent
import org.closs.core.presentation.shared.ui.components.navigation.BackHandlerComponent
import org.closs.core.presentation.shared.utils.calculateFABSize
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
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally,
        contentPadding = PaddingValues(8.dp)
    ) {
        when (state.isLoading) {
            true -> {
                item {
                    CircularLoadingComponent()
                }
            }
            false -> {
                state.order?.let { order ->
                    item {
                        OrderHeadComponent(order)
                    }

                    item {
                        HorizontalDivider()
                    }

                    items(order.lines) { line ->
                        OrderProductsListItem(
                            modifier = Modifier
                                .fillMaxWidth(),
                            orderLine = line
                        )
                    }

                    item {
                        Spacer(
                            modifier = Modifier.height(calculateFABSize())
                        )
                    }
                }
            }
        }
    }
}
