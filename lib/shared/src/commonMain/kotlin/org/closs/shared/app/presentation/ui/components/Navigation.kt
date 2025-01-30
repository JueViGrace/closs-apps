package org.closs.shared.app.presentation.ui.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.closs.core.presentation.shared.navigation.NavigationAction
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.presentation.shared.navigation.ObserveAsEvents
import org.closs.shared.app.presentation.viewmodel.AppViewModel
import org.jetbrains.compose.resources.getString
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Navigation(
    content: @Composable (
        NavHostController,
        Navigator,
        SnackbarHostState,
        AppViewModel,
    ) -> Unit,
) {
    val viewModel: AppViewModel = koinViewModel()
    val navController: NavHostController = rememberNavController()
    val navigator = koinInject<Navigator>()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(
        flow = navigator.navigationActions,
    ) { action ->
        when (action) {
            is NavigationAction.Navigate -> {
                navController.navigate(action.destination, navOptions = action.navOptions)
            }
            NavigationAction.NavigateUp -> {
                navController.navigateUp()
            }
        }
    }

    ObserveAsEvents(
        flow = viewModel.messages.messages,
    ) { msg ->
        scope.launch {
            snackBarHostState.showSnackbar(
                "${msg.message?.let { getString(it) }} ${msg.description ?: ""}"
            )
        }
    }

    ObserveAsEvents(
        flow = viewModel.state,
    ) { msg ->
        msg.snackMessage?.let { message ->
            scope.launch {
                snackBarHostState.showSnackbar("${getString(message)} ${msg.description}")
            }
        }
    }

    content(
        navController,
        navigator,
        snackBarHostState,
        viewModel
    )
}
