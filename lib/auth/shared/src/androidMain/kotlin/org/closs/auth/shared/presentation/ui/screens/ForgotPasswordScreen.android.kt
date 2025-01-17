package org.closs.auth.shared.presentation.ui.screens

import androidx.compose.runtime.Composable
import org.closs.auth.shared.presentation.viewmodel.ForgotPasswordViewModel
import org.closs.core.presentation.shared.ui.components.navigation.BackHandlerComponent

@Composable
actual fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel,
) {
    BackHandlerComponent(viewModel.navigator)
}
