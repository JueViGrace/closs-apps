package org.closs.shared.app.presentation.ui.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.closs.core.presentation.shared.navigation.NavigationAction
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.presentation.shared.navigation.ObserveAsEvents
import org.closs.shared.app.presentation.viewmodel.AppViewModel
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun Navigation(
    content: @Composable (
        NavHostController,
        Navigator,
    ) -> Unit,
) {
    val viewModel: AppViewModel = koinViewModel()
    val navController: NavHostController = rememberNavController()
    val navigator = koinInject<Navigator>()

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
        flow = viewModel.state,
    ) { _ -> }

    content(
        navController,
        navigator,
    )
}
