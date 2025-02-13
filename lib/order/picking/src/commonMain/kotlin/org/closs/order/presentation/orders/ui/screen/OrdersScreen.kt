package org.closs.order.presentation.orders.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import org.closs.core.presentation.shared.navigation.ObserveAsEvents
import org.closs.core.presentation.shared.ui.components.buttons.BackArrowButton
import org.closs.core.presentation.shared.ui.components.display.LazyColumnComponent
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.presentation.shared.ui.components.icons.IconComponent
import org.closs.core.presentation.shared.ui.components.layout.bars.TopBarComponent
import org.closs.core.presentation.shared.ui.components.layout.loading.LinearLoadingComponent
import org.closs.core.presentation.shared.ui.components.navigation.BackHandlerComponent
import org.closs.core.presentation.shared.utils.ScreenSize
import org.closs.core.presentation.shared.utils.calculateSmallIconSize
import org.closs.core.presentation.shared.utils.getScreenSize
import org.closs.core.presentation.shared.utils.toReadableDate
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.ic_refresh
import org.closs.order.presentation.components.OrderListItem
import org.closs.order.presentation.orders.events.OrdersEvents
import org.closs.order.presentation.orders.viewmodel.OrdersViewModel
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
        LazyColumnComponent(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            items = state.orders,
            grouped = state.orders.groupBy { it.emision },
            stickyHeader = { head ->
                head?.let { date ->
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp, bottom = 4.dp),
                    ) {
                        val cardPadding = when (getScreenSize()) {
                            ScreenSize.Compact -> 26.dp
                            ScreenSize.Medium -> 34.dp
                            ScreenSize.Large -> 44.dp
                        }
                        ElevatedCard(
                            modifier = Modifier.padding(start = cardPadding + 4.dp),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceTint,
                                contentColor = MaterialTheme.colorScheme.inverseOnSurface
                            )
                        ) {
                            TextComponent(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                text = date.toReadableDate(),
                            )
                        }
                    }
                }
            },
            content = { order ->
                when (state.isLoading) {
                    true -> {
                        LinearLoadingComponent()
                    }

                    false -> {
                        OrderListItem(
                            order = order,
                            onClick = {
                                viewModel.onEvent(OrdersEvents.NavigateToDetails(order.documento))
                            }
                        )
                    }
                }
            }

        )
    }
}
