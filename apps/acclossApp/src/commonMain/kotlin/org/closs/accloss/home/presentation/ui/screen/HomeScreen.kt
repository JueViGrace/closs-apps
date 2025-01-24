package org.closs.accloss.home.presentation.ui.screen

import androidx.compose.runtime.Composable
import org.closs.shared.home.presentation.viewmodel.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
expect fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel()
)
