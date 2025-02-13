package org.closs.order.presentation.history.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.closs.core.presentation.shared.ui.components.buttons.BackArrowButton
import org.closs.core.presentation.shared.ui.components.display.LazyColumnComponent
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.presentation.shared.ui.components.layout.bars.TopBarComponent
import org.closs.core.presentation.shared.ui.components.layout.loading.LinearLoadingComponent
import org.closs.core.presentation.shared.ui.components.navigation.BackHandlerComponent
import org.closs.core.presentation.shared.utils.ScreenSize
import org.closs.core.presentation.shared.utils.getScreenSize
import org.closs.core.presentation.shared.utils.toReadableDate
import org.closs.order.presentation.components.OrderListItem
import org.closs.order.presentation.history.events.HistoryEvents
import org.closs.order.presentation.history.viewmodel.HistoryViewModel
import org.closs.order.presentation.orders.events.OrdersEvents
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

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
                    // TODO: create actions
                }
            )
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
                                viewModel.onEvent(HistoryEvents.NavigateToDetails(order.documento))
                            }
                        )
                    }
                }
            }

        )
    }
}
