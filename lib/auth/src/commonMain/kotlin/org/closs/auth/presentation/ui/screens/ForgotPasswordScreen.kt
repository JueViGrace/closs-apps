package org.closs.auth.presentation.ui.screens

import androidx.compose.runtime.Composable
import org.closs.auth.presentation.viewmodel.ForgotPasswordViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
expect fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel = koinViewModel()
)
