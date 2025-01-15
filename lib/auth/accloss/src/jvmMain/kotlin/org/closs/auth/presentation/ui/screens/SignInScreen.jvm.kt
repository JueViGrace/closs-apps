package org.closs.auth.presentation.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.closs.auth.shared.presentation.ui.components.AuthBody
import org.closs.auth.shared.presentation.ui.components.AuthFooter
import org.closs.auth.shared.presentation.ui.components.AuthTitle
import org.closs.auth.shared.presentation.ui.components.desktop.DesktopAuthLayout
import org.closs.auth.shared.presentation.viewmodel.SignInViewModel
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.accloss_app_name
import org.closs.core.resources.resources.generated.resources.welcome_back
import org.jetbrains.compose.resources.stringResource

// todo: improve responsive
@Composable
actual fun SignInScreen(
    viewModel: SignInViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    DesktopAuthLayout(
        title = {
            AuthTitle(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.3f),
                title = {
                    TextComponent(
                        text = stringResource(Res.string.welcome_back),
                        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                        fontWeight = MaterialTheme.typography.headlineLarge.fontWeight,
                    )
                }
            )
        },
        content = {
            AuthBody(
                modifier = Modifier.fillMaxWidth(),
                state = state,
                onEvent = onEvent
            ) {
                // TODO: company text field
            }
        },
        footer = {
            AuthFooter(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.2f),
                title = stringResource(Res.string.accloss_app_name)
            )
        }
    )
}
