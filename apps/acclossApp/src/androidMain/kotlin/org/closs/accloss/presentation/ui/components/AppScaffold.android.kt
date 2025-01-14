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
import org.closs.app.shared.presentation.ui.screens.SplashScreen
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.presentation.shared.ui.components.buttons.BackArrowButton
import org.closs.core.presentation.shared.ui.components.layout.TopBarComponent

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
                Destination.Splash -> { }
                Destination.AuthGraph -> { }
                Destination.SignIn -> { }
                Destination.Accounts -> { }
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

                is Destination.OrderDetails -> { }
                Destination.Orders -> { }
                is Destination.ProductDetails -> { }
                Destination.Products -> { }
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
