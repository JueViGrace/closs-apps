package org.closs.picking.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.order.presentation.ui.screens.OrderDetailsScreen
import org.closs.order.presentation.ui.screens.OrdersListScreen
import org.closs.product.presentation.ui.screens.ProductDetailsScreen
import org.closs.product.presentation.ui.screens.ProductsListScreen

fun NavGraphBuilder.homeGraph() {
    navigation<Destination.HomeGraph>(
        startDestination = Destination.Home
    ) {
        composable<Destination.Home> {
        }

        composable<Destination.Settings> {
        }

        composable<Destination.Notifications> {
        }

        composable<Destination.Products> {
            ProductsListScreen()
        }

        composable<Destination.ProductDetails> {
            ProductDetailsScreen()
        }

        composable<Destination.Orders> {
            OrdersListScreen()
        }

        composable<Destination.OrderDetails> {
            OrderDetailsScreen()
        }
    }
}
