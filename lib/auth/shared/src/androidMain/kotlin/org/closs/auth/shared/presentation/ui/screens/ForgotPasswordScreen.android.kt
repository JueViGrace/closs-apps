package org.closs.auth.shared.presentation.ui.screens

import androidx.compose.runtime.Composable
import org.closs.core.presentation.shared.ui.components.navigation.BackHandlerComponent

@Composable
actual fun ForgotPasswordScreen(
    viewModel: org.closs.auth.shared.presentation.viewmodel.ForgotPasswordViewModel,
) {
    BackHandlerComponent(viewModel.navigator)
}
