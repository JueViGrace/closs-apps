package org.closs.picking.app.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.order.detail.presentation.ui.screen.OrderDetailScreen
import org.closs.order.history.presentation.ui.screen.HistoryScreen
import org.closs.order.presentation.ui.screen.OrdersScreen
import org.closs.picking.home.presentation.ui.screen.HomeScreen
import org.closs.picking.notifications.presentation.ui.screen.NotificationsScreen
import org.closs.picking.profile.presentation.ui.screen.ProfileScreen
import org.closs.picking.settings.presentation.ui.screen.SettingsScreen
import org.closs.product.presentation.ui.screens.ProductDetailsScreen
import org.closs.product.presentation.ui.screens.ProductsListScreen

fun NavGraphBuilder.homeGraph() {
    navigation<Destination.HomeGraph>(
        startDestination = Destination.Home
    ) {
        composable<Destination.Home> {
            HomeScreen()
        }

        composable<Destination.Settings> {
            SettingsScreen()
        }

        composable<Destination.Notifications> {
            NotificationsScreen()
        }

        composable<Destination.Profile> {
            ProfileScreen()
        }

        composable<Destination.PickingHistory> {
            HistoryScreen()
        }

        composable<Destination.PendingOrders> {
            OrdersScreen()
        }

        composable<Destination.OrderDetails> { backStackEntry ->
            val id: String = backStackEntry.toRoute()
            OrderDetailScreen(
                orderId = id
            )
        }

        composable<Destination.Products> {
            ProductsListScreen()
        }

        composable<Destination.ProductDetails> { backStackEntry ->
            val id: String = backStackEntry.toRoute()
            ProductDetailsScreen(
                productId = id
            )
        }
    }
}
