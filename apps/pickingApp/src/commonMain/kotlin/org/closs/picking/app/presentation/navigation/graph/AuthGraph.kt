package org.closs.picking.app.presentation.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.closs.auth.presentation.ui.screens.SignInScreen
import org.closs.auth.shared.presentation.ui.screens.ForgotPasswordScreen
import org.closs.core.presentation.shared.navigation.Destination

fun NavGraphBuilder.authGraph() {
    navigation<Destination.AuthGraph>(
        startDestination = Destination.SignIn
    ) {
        composable<Destination.SignIn> {
            SignInScreen()
        }

        composable<Destination.ForgotPassword> {
            ForgotPasswordScreen()
        }
    }
}
