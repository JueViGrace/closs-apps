package org.closs.auth.shared.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.closs.auth.shared.presentation.viewmodel.ForgotPasswordViewModel
import org.closs.core.presentation.shared.ui.components.buttons.BackArrowButton
import org.closs.core.presentation.shared.ui.components.layout.bars.TopBarComponent
import org.closs.core.presentation.shared.ui.components.navigation.BackHandlerComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
actual fun ForgotPasswordScreen(
    viewModel: ForgotPasswordViewModel,
) {
    val scope = rememberCoroutineScope()

    BackHandlerComponent(viewModel.navigator)
    Scaffold(
        topBar = {
            TopBarComponent(
                navigationIcon = {
                    BackArrowButton {
                        scope.launch {
                            viewModel.navigator.navigateUp()
                        }
                    }
                },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
        }
    }
}
