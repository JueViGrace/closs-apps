package org.closs.auth.presentation.ui.screens

import androidx.compose.runtime.Composable
import org.closs.auth.shared.presentation.viewmodel.ForgotPasswordViewModel
import org.closs.core.presentation.ui.components.display.TextComponent

@Composable
actual fun ForgotPasswordScreen(
    viewModel: org.closs.auth.shared.presentation.viewmodel.ForgotPasswordViewModel
) {
    TextComponent(text = "hola")
}
