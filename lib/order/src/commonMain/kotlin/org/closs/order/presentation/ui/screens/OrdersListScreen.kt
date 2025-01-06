package org.closs.order.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.closs.core.presentation.ui.components.display.TextComponent
import org.closs.order.presentation.viewmodel.OrdersListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OrdersListScreen(
    viewModel: OrdersListViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextComponent(
            text = "Orders List Screen",
        )
    }
}
