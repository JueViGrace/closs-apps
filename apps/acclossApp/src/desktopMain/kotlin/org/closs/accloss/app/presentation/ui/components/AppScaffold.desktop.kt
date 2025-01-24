package org.closs.accloss.app.presentation.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import org.closs.accloss.app.presentation.navigation.graph.authGraph
import org.closs.accloss.app.presentation.navigation.graph.homeGraph
import org.closs.shared.app.presentation.state.AppState
import org.closs.shared.app.presentation.ui.screens.SplashScreen
import org.closs.shared.app.presentation.viewmodel.AppViewModel
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.NavigationStack
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.presentation.shared.ui.components.buttons.BackArrowButton
import org.closs.core.presentation.shared.ui.components.layout.bars.TopBarComponent
import org.closs.core.presentation.shared.ui.components.layout.bars.actions.HomeTopBarActions

@Composable
actual fun AppScaffold(
    modifier: Modifier,
    navController: NavHostController,
    navigator: Navigator,
    snackBarHostState: SnackbarHostState,
    viewModel: AppViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val stack by navigator.stack.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopBar(
                state = state,
                viewModel = viewModel,
                navigator = navigator,
                stack = stack,
            )
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    state: AppState,
    viewModel: AppViewModel,
    navigator: Navigator,
    stack: NavigationStack,
) {
    val scope = rememberCoroutineScope()
    when (stack.currentDestination) {
        Destination.ForgotPassword -> {
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

        Destination.Home -> {
            TopBarComponent(
                actions = {
                    HomeTopBarActions(
                        accountLetter = state.session?.name?.firstOrNull()?.toString() ?: "P",
                        onNotificationsClick = {
                            viewModel.navigateToNotifications()
                        },
                        onAccountClick = {
                            viewModel.toggleDialog()
                        }
                    )
                }
            )
        }
        Destination.Profile -> {
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

        Destination.Products -> {
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
        is Destination.ProductDetails -> {
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
        Destination.Orders -> {
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
        is Destination.OrderDetails -> {
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
        null -> {}
        else -> {}
    }
}
