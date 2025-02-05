package org.closs.order.detail.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.closs.core.presentation.shared.actions.FABActions
import org.closs.core.presentation.shared.navigation.ObserveAsEvents
import org.closs.core.presentation.shared.ui.components.buttons.BackArrowButton
import org.closs.core.presentation.shared.ui.components.buttons.FABComponent
import org.closs.core.presentation.shared.ui.components.layout.bars.TopBarComponent
import org.closs.core.presentation.shared.ui.components.layout.loading.CircularLoadingComponent
import org.closs.core.presentation.shared.ui.components.navigation.BackHandlerComponent
import org.closs.core.presentation.shared.utils.calculateFABSize
import org.closs.order.detail.presentation.events.OrderDetailEvents
import org.closs.order.detail.presentation.ui.components.OrderHeadComponent
import org.closs.order.detail.presentation.ui.components.OrderProductsListItem
import org.closs.order.detail.presentation.viewmodel.OrderDetailViewModel
import org.jetbrains.compose.resources.getString
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetailScreen(
    orderId: String,
    viewModel: OrderDetailViewModel = koinViewModel(
        parameters = { parametersOf(orderId) }
    )
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
            )
        },
        floatingActionButton = {
            FABComponent(
                showMenu = state.showFABMenu,
                toggleMenu = {
                    viewModel.onEvent(OrderDetailEvents.ToggleFABMenu)
                },
                actions = listOf(FABActions.PickUp),
                onActionClick = { action ->
                    viewModel.onEvent(OrderDetailEvents.OnFABActionClick(action))
                }
            )
        },
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
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
}
