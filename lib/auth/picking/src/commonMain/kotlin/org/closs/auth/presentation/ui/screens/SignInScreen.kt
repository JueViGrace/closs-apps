package org.closs.auth.presentation.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import org.closs.auth.shared.presentation.ui.components.AuthBody
import org.closs.auth.shared.presentation.ui.components.AuthFooter
import org.closs.auth.shared.presentation.ui.components.AuthTitle
import org.closs.auth.shared.presentation.ui.components.mobile.MobileAuthLayout
import org.closs.auth.shared.presentation.viewmodel.SignInViewModel
import org.closs.core.presentation.shared.navigation.ObserveAsEvents
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.picking_app_name
import org.closs.core.resources.resources.generated.resources.please_log_in
import org.closs.core.resources.resources.generated.resources.welcome_back
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.koin.androidx.compose.koinViewModel

// todo: responsive
@Composable
fun SignInScreen(
    viewModel: SignInViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    ObserveAsEvents(
        flow = viewModel.messages.messages,
    ) { msg ->
        scope.launch {
            snackBarHostState.showSnackbar(
                "${msg.message?.let { getString(it) }} ${msg.description ?: ""}"
            )
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackBarHostState)
        }
    ) { innerPadding ->
        MobileAuthLayout(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 12.dp),
            title = {
                AuthTitle(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    title = {
                        TextComponent(
                            text = stringResource(Res.string.welcome_back),
                            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                            fontWeight = MaterialTheme.typography.headlineMedium.fontWeight,
                        )
                        TextComponent(
                            text = stringResource(Res.string.please_log_in),
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                        )
                    }
                )
            },
            content = {
                AuthBody(
                    modifier = Modifier.fillMaxWidth(),
                    state = state,
                    onEvent = onEvent
                )
            },
            footer = {
                AuthFooter(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp),
                    title = stringResource(Res.string.picking_app_name)
                )
            }
        )
    }
}
