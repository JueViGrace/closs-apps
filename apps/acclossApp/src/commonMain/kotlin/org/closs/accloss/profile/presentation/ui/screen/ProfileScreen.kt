package org.closs.accloss.profile.presentation.ui.screen

import androidx.compose.runtime.Composable
import org.closs.shared.profile.presentation.viewmodel.ProfileViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel()
) {
}
