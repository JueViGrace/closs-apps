package org.closs.auth.presentation.ui.screens

import androidx.compose.runtime.Composable
import org.closs.auth.shared.presentation.viewmodel.ForgotPasswordViewModel
import org.closs.core.presentation.ui.components.navigation.BackHandlerComponent

@Composable
actual fun ForgotPasswordScreen(
    viewModel: org.closs.auth.shared.presentation.viewmodel.ForgotPasswordViewModel,
) {
    BackHandlerComponent(viewModel.navigator)
}
