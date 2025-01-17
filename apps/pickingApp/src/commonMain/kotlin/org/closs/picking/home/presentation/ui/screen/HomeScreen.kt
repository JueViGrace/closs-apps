package org.closs.picking.home.presentation.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.greetings
import org.closs.core.resources.resources.generated.resources.ic_arrow_big_right
import org.closs.core.resources.resources.generated.resources.ic_avlogo
import org.closs.core.resources.resources.generated.resources.ic_shopping_bag
import org.closs.core.resources.resources.generated.resources.pending_orders
import org.closs.core.resources.resources.generated.resources.picking_history
import org.closs.picking.home.presentation.ui.components.DashboardNavItem
import org.closs.picking.home.presentation.viewmodel.HomeViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.4f)
                .background(
                    color = MaterialTheme.colorScheme.surfaceContainer,
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextComponent(
                text = "${stringResource(Res.string.greetings)}, Picker",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                fontWeight = MaterialTheme.typography.titleMedium.fontWeight
            )
            TextComponent(
                text = "${stringResource(Res.string.pending_orders)} xx",
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight
            )
        }

        DashboardNavItem(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .clickable {
                    viewModel.navigateToPickingHistory()
                }
                .padding(horizontal = 16.dp),
            title = stringResource(Res.string.picking_history),
            image = painterResource(Res.drawable.ic_avlogo),
            icon = painterResource(Res.drawable.ic_arrow_big_right)
        )
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        )
        DashboardNavItem(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .clickable {
                    viewModel.navigateToPendingOrders()
                }
                .padding(horizontal = 16.dp),
            title = stringResource(Res.string.pending_orders),
            image = painterResource(Res.drawable.ic_avlogo),
            icon = painterResource(Res.drawable.ic_arrow_big_right)
        )
    }
}
