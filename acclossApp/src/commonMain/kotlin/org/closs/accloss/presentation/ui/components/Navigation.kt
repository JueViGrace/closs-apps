package org.closs.accloss.presentation.ui.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.closs.accloss.presentation.viewmodel.AppViewModel
import org.closs.core.presentation.navigation.NavigationAction
import org.closs.core.presentation.navigation.Navigator
import org.closs.core.presentation.navigation.ObserveAsEvents
import org.jetbrains.compose.resources.getString
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    appViewModel: AppViewModel = koinViewModel()
) {
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
        flow = appViewModel.messages,
    ) { msg ->
        msg.message?.let { message ->
            scope.launch {
                snackBarHostState.showSnackbar("${getString(message)} ${msg.description ?: ""}")
            }
        }
    }

    ObserveAsEvents(
        flow = appViewModel.state,
    ) { msg ->
        msg.snackMessage?.let { message ->
            scope.launch {
                snackBarHostState.showSnackbar("${getString(message)} ${msg.description}")
            }
        }
    }

    AppScaffold(
        modifier = modifier,
        navController = navController,
        navigator = navigator,
        snackBarHostState = snackBarHostState
    )
}
