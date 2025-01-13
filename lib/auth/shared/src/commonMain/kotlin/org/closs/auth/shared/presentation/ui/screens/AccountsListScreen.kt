package org.closs.auth.shared.presentation.ui.screens

import androidx.compose.runtime.Composable
import org.closs.auth.presentation.viewmodel.AccountsListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
expect fun AccountsListScreen(
    viewModel: AccountsListViewModel = koinViewModel()
)
