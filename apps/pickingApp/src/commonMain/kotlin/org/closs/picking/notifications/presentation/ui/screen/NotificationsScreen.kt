package org.closs.picking.notifications.presentation.ui.screen

import androidx.compose.runtime.Composable
import org.closs.core.presentation.shared.ui.components.navigation.BackHandlerComponent
import org.closs.shared.notifications.presentation.viewmodel.NotificationsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NotificationsScreen(
    viewModel: NotificationsViewModel = koinViewModel()
) {
    BackHandlerComponent(viewModel.navigator)
}
