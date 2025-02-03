package org.closs.picking.app.presentation.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.picking.app.presentation.navigation.graph.authGraph
import org.closs.picking.app.presentation.navigation.graph.homeGraph
import org.closs.shared.app.presentation.ui.screens.SplashScreen
import org.closs.shared.app.presentation.viewmodel.AppViewModel

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
        floatingActionButton = {
            FABComponent(
                viewModel = viewModel,
                navigator = navigator,
                stack = stack
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

