package org.closs.auth.shared.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import org.closs.auth.shared.presentation.events.SignInEvents
import org.closs.auth.shared.presentation.state.SignInState
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.forgot_password
import org.closs.core.resources.resources.generated.resources.log_in
import org.jetbrains.compose.resources.stringResource

@Composable
fun AuthBody(
    modifier: Modifier = Modifier,
    state: SignInState,
    onEvent: (SignInEvents) -> Unit,
    companyContent: (@Composable () -> Unit)? = null,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(18.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            companyContent?.let { content -> content() }

            UsernameTextField(
                value = state.username,
                onValueChange = { newValue ->
                    onEvent(SignInEvents.OnUsernameChanged(newValue))
                },
                errorMessage = state.usernameError?.let { stringResource(it) },
                enabled = state.signInEnabled,
                isError = state.usernameError != null
            )

            PasswordTextField(
                value = state.password,
                onValueChange = { newValue ->
                    onEvent(SignInEvents.OnPasswordChanged(newValue))
                },
                errorMessage = state.passwordError?.let { stringResource(it) },
                enabled = state.signInEnabled,
                isError = state.usernameError != null,
                passwordVisibility = state.passwordVisibility,
                onVisibilityChange = {
                    onEvent(SignInEvents.TogglePasswordVisibility)
                }
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 6.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextComponent(
                modifier = Modifier.clickable {
                    onEvent(SignInEvents.OnNavigateToForgotPassword)
                },
                text = stringResource(Res.string.forgot_password),
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.secondary
            )
        }

        OutlinedButton(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            onClick = {
                onEvent(SignInEvents.OnSignInSubmit)
            },
            enabled = state.signInSubmitEnabled
        ) {
            TextComponent(
                text = stringResource(Res.string.log_in)
            )
        }
    }
}
