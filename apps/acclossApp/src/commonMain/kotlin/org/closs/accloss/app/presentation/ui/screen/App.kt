package org.closs.accloss.presentation.ui.app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.closs.accloss.presentation.ui.components.AppScaffold
import org.closs.app.shared.presentation.ui.components.Navigation
import org.closs.core.presentation.shared.ui.theme.AppTheme
import org.koin.compose.KoinContext

@Composable
fun App() {
    KoinContext {
        AppTheme {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                Navigation { navController, navigator, snackBarHostState ->
                    AppScaffold(
                        navController = navController,
                        navigator = navigator,
                        snackBarHostState = snackBarHostState
                    )
                }
            }
        }
    }
}
