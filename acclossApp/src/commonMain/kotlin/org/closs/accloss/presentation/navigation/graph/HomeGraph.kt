package org.closs.accloss.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.closs.core.presentation.navigation.Destination
import org.closs.order.presentation.ui.screens.OrderDetailsScreen
import org.closs.order.presentation.ui.screens.OrdersListScreen
import org.closs.product.presentation.ui.screens.ProductDetailsScreen
import org.closs.product.presentation.ui.screens.ProductsListScreen
import org.closs.user.presentation.ui.screens.UserDetailsScreen
import org.closs.user.presentation.ui.screens.UsersListScreen

fun NavGraphBuilder.homeGraph() {
    navigation<org.closs.core.presentation.navigation.Destination.HomeGraph>(
        startDestination = org.closs.core.presentation.navigation.Destination.Home
    ) {
        composable<org.closs.core.presentation.navigation.Destination.Home> {
        }

        composable<org.closs.core.presentation.navigation.Destination.Profile> {
        }

        composable<org.closs.core.presentation.navigation.Destination.Settings> {
        }

        composable<org.closs.core.presentation.navigation.Destination.Users> {
            UsersListScreen()
        }

        composable<org.closs.core.presentation.navigation.Destination.UserDetails> {
            UserDetailsScreen()
        }

        composable<org.closs.core.presentation.navigation.Destination.Products> {
            ProductsListScreen()
        }

        composable<org.closs.core.presentation.navigation.Destination.ProductDetails> {
            ProductDetailsScreen()
        }

        composable<org.closs.core.presentation.navigation.Destination.Notifications> {
        }

        composable<org.closs.core.presentation.navigation.Destination.Cart> {
        }

        composable<org.closs.core.presentation.navigation.Destination.Checkout> {
        }

        composable<org.closs.core.presentation.navigation.Destination.Orders> {
            OrdersListScreen()
        }

        composable<org.closs.core.presentation.navigation.Destination.OrderDetails> {
            OrderDetailsScreen()
        }
    }
}
