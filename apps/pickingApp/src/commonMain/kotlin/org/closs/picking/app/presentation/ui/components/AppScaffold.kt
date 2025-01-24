package org.closs.picking.app.presentation.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.launch
import org.closs.shared.app.presentation.state.AppState
import org.closs.shared.app.presentation.ui.screens.SplashScreen
import org.closs.shared.app.presentation.viewmodel.AppViewModel
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.NavigationStack
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.presentation.shared.ui.components.buttons.BackArrowButton
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.presentation.shared.ui.components.layout.bars.TopBarComponent
import org.closs.core.presentation.shared.ui.components.layout.bars.actions.HomeTopBarActions
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.notifications
import org.closs.picking.app.presentation.navigation.graph.authGraph
import org.closs.picking.app.presentation.navigation.graph.homeGraph
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
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
                },
            )
        }

        Destination.Home -> {
            TopBarComponent(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                ),
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
                title = {
                    TextComponent(
                        text = stringResource(Res.string.notifications),
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                    )
                },
                navigationIcon = {
                    BackArrowButton {
                        scope.launch {
                            navigator.navigateUp()
                        }
                    }
                },
            )
        }
        Destination.PickingHistory -> {
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
        Destination.PendingOrders -> {
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
        Destination.Products -> { }
        is Destination.ProductDetails -> { }
        null -> { }
        else -> {}
    }
}
