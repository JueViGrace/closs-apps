package org.closs.accloss.app.presentation.ui.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import org.closs.app.shared.presentation.viewmodel.AppViewModel
import org.closs.core.presentation.shared.navigation.Navigator

@Composable
expect fun AppScaffold(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navigator: Navigator,
    snackBarHostState: SnackbarHostState,
    viewModel: AppViewModel
)
