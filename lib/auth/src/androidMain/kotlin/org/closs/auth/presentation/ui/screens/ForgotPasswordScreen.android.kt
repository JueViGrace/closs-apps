package org.closs.auth.presentation.ui.screens

import androidx.compose.runtime.Composable
import org.closs.auth.presentation.viewmodel.ForgotPasswordViewModel
import org.closs.core.presentation.ui.components.navigation.BackHandlerComponent

@Composable
actual fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel,
) {
    BackHandlerComponent(viewModel.navigator)
}
