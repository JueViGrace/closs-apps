package org.closs.accloss.presentation.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch
import org.closs.accloss.presentation.navigation.graph.authGraph
import org.closs.accloss.presentation.navigation.graph.homeGraph
import org.closs.accloss.presentation.ui.screens.SplashScreen
import org.closs.core.presentation.navigation.Navigator
import org.closs.core.presentation.ui.components.buttons.BackArrowButton
import org.closs.core.presentation.ui.components.layout.TopBarComponent

// TODO: limit access to screens
@Composable
actual fun AppScaffold(
    modifier: Modifier,
    navController: NavHostController,
    navigator: Navigator,
    snackBarHostState: SnackbarHostState,
) {
    val stack by navigator.stack.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            when (stack.currentDestination) {
                org.closs.core.presentation.navigation.Destination.AuthGraph -> {}
                org.closs.core.presentation.navigation.Destination.Cart -> {}
                org.closs.core.presentation.navigation.Destination.Checkout -> {}
                org.closs.core.presentation.navigation.Destination.ForgotPassword -> {
                    TopBarComponent(
                        navigationIcon = {
                            BackArrowButton {
                                scope.launch {
                                    navigator.navigateUp()
                                }
                            }
                        }
                    )
                }
                org.closs.core.presentation.navigation.Destination.Home -> {}
                org.closs.core.presentation.navigation.Destination.HomeGraph -> {}
                org.closs.core.presentation.navigation.Destination.Notifications -> {}
                is org.closs.core.presentation.navigation.Destination.OrderDetails -> {}
                org.closs.core.presentation.navigation.Destination.Orders -> {}
                is org.closs.core.presentation.navigation.Destination.ProductDetails -> {}
                org.closs.core.presentation.navigation.Destination.Products -> {}
                org.closs.core.presentation.navigation.Destination.Profile -> {}
                org.closs.core.presentation.navigation.Destination.Settings -> {}
                org.closs.core.presentation.navigation.Destination.SignIn -> {}
                org.closs.core.presentation.navigation.Destination.Accounts -> {}
                org.closs.core.presentation.navigation.Destination.Splash -> {}
                is org.closs.core.presentation.navigation.Destination.UserDetails -> {}
                org.closs.core.presentation.navigation.Destination.Users -> {}
                null -> {}
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        NavHost(
            modifier = modifier.padding(innerPadding),
            navController = navController,
            startDestination = navigator.startDestination,
        ) {
            composable<org.closs.core.presentation.navigation.Destination.Splash> {
                SplashScreen()
            }
            authGraph()
            homeGraph()
        }
    }
}
