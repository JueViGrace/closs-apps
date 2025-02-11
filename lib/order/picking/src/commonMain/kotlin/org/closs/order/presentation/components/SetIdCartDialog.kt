package org.closs.order.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.presentation.shared.ui.components.layout.loading.LinearLoadingComponent
import org.closs.core.presentation.shared.utils.calculateLabelFontSize
import org.closs.core.presentation.shared.utils.calculateLabelFontWeight
import org.closs.core.presentation.shared.utils.calculateLargeFontSize
import org.closs.core.presentation.shared.utils.calculateLargeFontWeight
import org.closs.core.presentation.shared.utils.calculateMaxDialogSize
import org.closs.core.presentation.shared.utils.calculateMediumFontSize
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.before_continuing
import org.closs.core.resources.resources.generated.resources.dismiss
import org.closs.core.resources.resources.generated.resources.ok
import org.closs.core.resources.resources.generated.resources.token
import org.closs.core.resources.resources.generated.resources.token_placeholder
import org.closs.core.resources.resources.generated.resources.type_cart_id
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SetIdCartDialog(
    onDismiss: () -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: StringResource?,
    isError: Boolean,
    submitEnabled: Boolean,
    cartLoading: Boolean,
    onSubmit: () -> Unit
) {
    val focusManager = LocalFocusManager.current

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = calculateMaxDialogSize())
                .background(
                    color = AlertDialogDefaults.containerColor,
                    shape = AlertDialogDefaults.shape
                )
                .clip(AlertDialogDefaults.shape)
                .padding(28.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextComponent(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.before_continuing),
                fontSize = calculateLargeFontSize(),
                fontWeight = calculateLargeFontWeight(),
                color = MaterialTheme.colorScheme.primary
            )
            TextComponent(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.type_cart_id),
                fontSize = calculateMediumFontSize(),
                maxLines = 3
            )

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = value,
                onValueChange = { newValue ->
                    if (newValue.any { !it.isDigit() }) {
                        onValueChange(value)
                    } else {
                        onValueChange(newValue)
                    }
                },
                label = {
                    TextComponent(
                        text = stringResource(Res.string.token)
                    )
                },
                placeholder = {
                    TextComponent(
                        text = stringResource(Res.string.token_placeholder)
                    )
                },
                supportingText = if (errorMessage != null) {
                    {
                        TextComponent(
                            text = stringResource(errorMessage),
                            fontSize = calculateLabelFontSize(),
                            fontWeight = calculateLabelFontWeight(),
                        )
                    }
                } else {
                    null
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
                isError = isError
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = onDismiss
                ) {
                    TextComponent(
                        text = stringResource(Res.string.dismiss)
                    )
                }

                ElevatedButton(
                    onClick = onSubmit,
                    enabled = submitEnabled,
                ) {
                    TextComponent(
                        text = stringResource(Res.string.ok)
                    )
                }
            }

            if (cartLoading) {
                LinearLoadingComponent()
            }
        }
    }
}
