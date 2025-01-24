package org.closs.accloss.home.presentation.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.closs.accloss.home.presentation.ui.components.ACHomeDialog
import org.closs.shared.home.presentation.viewmodel.HomeViewModel

@Composable
actual fun HomeScreen(viewModel: HomeViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.showDialog) {
        ACHomeDialog(
            state = state,
            onEvent = viewModel::onEvent
        )
    }
}
