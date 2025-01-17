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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.closs.app.shared.presentation.state.AppState
import org.closs.app.shared.presentation.ui.screens.SplashScreen
import org.closs.app.shared.presentation.viewmodel.AppViewModel
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.NavigationStack
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.presentation.shared.ui.components.buttons.AccountButton
import org.closs.core.presentation.shared.ui.components.buttons.BackArrowButton
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.presentation.shared.ui.components.layout.TopBarComponent
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
    val scope = rememberCoroutineScope()

    // todo: make dialog

    Scaffold(
        topBar = {
            TopBar(
                state = state,
                navigator = navigator,
                stack = stack,
                scope = scope
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
    navigator: Navigator,
    stack: NavigationStack,
    scope: CoroutineScope,
) {
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
                    AccountButton(
                        letter = state.session?.user?.name?.firstOrNull()?.toString() ?: "P",
                    ) {
                        // todo: account settings dialog menu
                    }
                }
            )
        }
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
