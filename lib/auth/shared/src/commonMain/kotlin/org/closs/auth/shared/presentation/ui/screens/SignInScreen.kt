package org.closs.auth.shared.presentation.ui.screens

import androidx.compose.runtime.Composable
import org.closs.auth.shared.presentation.viewmodel.SignInViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
expect fun SignInScreen(
    viewModel: SignInViewModel = koinViewModel()
)
