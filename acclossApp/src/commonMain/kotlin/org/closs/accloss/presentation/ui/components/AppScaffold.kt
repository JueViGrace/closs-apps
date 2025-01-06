package org.closs.accloss.presentation.ui.components

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import org.closs.core.presentation.navigation.Navigator

@Composable
expect fun AppScaffold(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    navigator: Navigator,
    snackBarHostState: SnackbarHostState,
)
