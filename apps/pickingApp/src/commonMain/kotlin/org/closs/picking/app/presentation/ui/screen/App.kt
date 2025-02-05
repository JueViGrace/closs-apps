package org.closs.picking.app.presentation.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.ui.theme.AppTheme
import org.closs.picking.app.presentation.navigation.graph.authGraph
import org.closs.picking.app.presentation.navigation.graph.homeGraph
import org.closs.shared.app.presentation.ui.components.Navigation
import org.closs.shared.app.presentation.ui.screens.SplashScreen
import org.koin.compose.KoinContext

@Composable
fun App() {
    KoinContext {
        AppTheme {
            Surface(
                modifier = Modifier.fillMaxSize()
            ) {
                Navigation { navController, navigator ->
                    NavHost(
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
        }
    }
}
