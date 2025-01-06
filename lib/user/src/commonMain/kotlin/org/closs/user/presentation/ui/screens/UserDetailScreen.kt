package org.closs.user.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.closs.core.presentation.ui.components.display.TextComponent
import org.closs.user.presentation.viewmodel.UserDetailsViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun UserDetailsScreen(
    viewModel: UserDetailsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextComponent(
            text = "User Details Screen",
        )
    }
}
