package org.closs.picking.profile.presentation.ui.screen

import androidx.compose.runtime.Composable
import org.closs.core.presentation.shared.ui.components.navigation.BackHandlerComponent
import org.closs.shared.profile.presentation.viewmodel.ProfileViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = koinViewModel()
) {
    BackHandlerComponent(viewModel.navigator)
}
