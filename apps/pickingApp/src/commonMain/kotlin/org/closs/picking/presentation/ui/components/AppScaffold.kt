package org.closs.picking.presentation.ui.components

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
import org.closs.picking.presentation.navigation.graph.authGraph
import org.closs.app.shared.presentation.ui.screens.SplashScreen
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.presentation.shared.ui.components.buttons.BackArrowButton
import org.closs.core.presentation.shared.ui.components.layout.TopBarComponent
import org.closs.picking.presentation.navigation.graph.homeGraph

// TODO: limit access to screens
@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navigator: Navigator,
    snackBarHostState: SnackbarHostState,
) {
    val stack by navigator.stack.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            when (stack.currentDestination) {
                Destination.Splash -> { }
                Destination.AuthGraph -> { }
                Destination.SignIn -> { }
                Destination.ForgotPassword -> {
                    TopBarComponent(
                        navigationIcon = {
                            BackArrowButton {
                                scope.launch {
                                    navigator.navigateUp()
                                }
                            }
                        },
                    )
                }

                Destination.HomeGraph -> { }
                Destination.Home -> { }
                Destination.Profile -> { }
                Destination.Settings -> {
                    TopBarComponent(
                        navigationIcon = {
                            BackArrowButton {
                                scope.launch {
                                    navigator.navigateUp()
                                }
                            }
                        },
                    )
                }
                Destination.Notifications -> {
                    TopBarComponent(
                        navigationIcon = {
                            BackArrowButton {
                                scope.launch {
                                    navigator.navigateUp()
                                }
                            }
                        },
                    )
                }
                Destination.Products -> { }
                is Destination.ProductDetails -> { }
                Destination.Orders -> { }
                is Destination.OrderDetails -> { }
                null -> { }
                else -> {}
            }
        },
        bottomBar = {
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
            composable<Destination.Splash> {
                SplashScreen()
            }
            authGraph()
            homeGraph()
        }
    }
}
