package org.closs.picking.settings.presentation.ui.screen

import androidx.compose.runtime.Composable
import org.closs.core.presentation.shared.ui.components.navigation.BackHandlerComponent
import org.closs.shared.settings.presentation.viewmodel.SettingsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = koinViewModel()
){
    BackHandlerComponent(viewModel.navigator)

}
