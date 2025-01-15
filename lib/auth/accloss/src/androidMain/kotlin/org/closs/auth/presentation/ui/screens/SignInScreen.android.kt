package org.closs.auth.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.closs.auth.shared.presentation.events.SignInEvents
import org.closs.auth.shared.presentation.ui.components.AuthBody
import org.closs.auth.shared.presentation.ui.components.AuthFooter
import org.closs.auth.shared.presentation.ui.components.AuthTitle
import org.closs.auth.shared.presentation.ui.components.CompanyTextField
import org.closs.auth.shared.presentation.ui.components.mobile.MobileAuthLayout
import org.closs.auth.shared.presentation.viewmodel.SignInViewModel
import org.closs.core.presentation.shared.ui.components.buttons.RowButton
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.accloss_app_name
import org.closs.core.resources.resources.generated.resources.please_log_in
import org.closs.core.resources.resources.generated.resources.submit
import org.closs.core.resources.resources.generated.resources.welcome_back
import org.jetbrains.compose.resources.stringResource

@Composable
actual fun SignInScreen(
    viewModel: SignInViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onEvent = viewModel::onEvent
    MobileAuthLayout(
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
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CompanyTextField(
                        modifier = Modifier.weight(0.7f),
                        value = state.company,
                        onValueChange = { onEvent(SignInEvents.OnCompanyChanged(it)) },
                        errorMessage = state.companyError?.let { stringResource(it) },
                        enabled = !state.isLoading,
                        isError = state.companyError != null
                    )

                    OutlinedButton(
                        modifier = Modifier.weight(0.3f),
                        onClick = {
                            onEvent(SignInEvents.OnCompanySubmitted)
                        }
                    ) {
                        TextComponent(text = stringResource(Res.string.submit))
                    }
                }
            }
        },
        footer = {
            AuthFooter(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                title = stringResource(Res.string.accloss_app_name)
            )
        }
    )
}
