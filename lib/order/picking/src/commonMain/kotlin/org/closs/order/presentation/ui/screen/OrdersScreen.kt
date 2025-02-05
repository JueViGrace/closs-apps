package org.closs.order.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.closs.core.presentation.shared.navigation.ObserveAsEvents
import org.closs.core.presentation.shared.ui.components.buttons.BackArrowButton
import org.closs.core.presentation.shared.ui.components.icons.IconComponent
import org.closs.core.presentation.shared.ui.components.layout.bars.TopBarComponent
import org.closs.core.presentation.shared.ui.components.layout.loading.LinearLoadingComponent
import org.closs.core.presentation.shared.ui.components.navigation.BackHandlerComponent
import org.closs.core.presentation.shared.utils.calculateSmallIconSize
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.ic_refresh
import org.closs.order.presentation.events.OrdersEvents
import org.closs.order.presentation.ui.components.OrderListItem
import org.closs.order.presentation.viewmodel.OrdersViewModel
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(
    viewModel: OrdersViewModel = koinViewModel()
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

    BackHandlerComponent(viewModel.navigator)
    Scaffold(
        topBar = {
            TopBarComponent(
                navigationIcon = {
                    BackArrowButton {
                        scope.launch {
                            viewModel.navigator.navigateUp()
                        }
                    }
                },
                actions = {
                    // todo: change for menu actions or add fab to display options
                    // filtering, reload
                    IconComponent(
                        iconModifier = Modifier.size(calculateSmallIconSize()),
                        painter = painterResource(Res.drawable.ic_refresh),
                        onClick = {
                            viewModel.onEvent(OrdersEvents.Refresh)
                        }
                    )
                },
            )
        },
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
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
                                viewModel.onEvent(OrdersEvents.NavigateToDetails(order.documento))
                            }
                        )
                    }
                }
            }
        }
    }
}
