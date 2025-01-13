package org.closs.auth.shared.presentation.ui.screens

import androidx.compose.runtime.Composable
import org.closs.auth.shared.presentation.viewmodel.ForgotPasswordViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
expect fun ForgotPasswordScreen(
    viewModel: org.closs.auth.shared.presentation.viewmodel.ForgotPasswordViewModel = koinViewModel()
)
