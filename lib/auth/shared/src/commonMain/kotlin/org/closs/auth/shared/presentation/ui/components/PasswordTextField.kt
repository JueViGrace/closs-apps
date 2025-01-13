package org.closs.auth.shared.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import org.closs.core.presentation.ui.components.display.TextComponent
import org.closs.core.presentation.ui.components.icons.IconComponent
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.hide_password
import org.closs.core.resources.resources.generated.resources.ic_eye
import org.closs.core.resources.resources.generated.resources.ic_eye_off
import org.closs.core.resources.resources.generated.resources.ic_lock
import org.closs.core.resources.resources.generated.resources.password
import org.closs.core.resources.resources.generated.resources.password_text_field_icon
import org.closs.core.resources.resources.generated.resources.show_password
import org.closs.core.resources.resources.generated.resources.your_password
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String? = null,
    enabled: Boolean = true,
    isError: Boolean = errorMessage != null,
    passwordVisibility: Boolean = false,
    onVisibilityChange: () -> Unit,
) {
    val focus = LocalFocusManager.current
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        label = {
            TextComponent(text = stringResource(Res.string.password))
        },
        placeholder = {
            TextComponent(text = stringResource(Res.string.your_password))
        },
        leadingIcon = {
            IconComponent(
                painter = painterResource(Res.drawable.ic_lock),
                contentDescription = stringResource(Res.string.password_text_field_icon)
            )
        },
        trailingIcon = {
            IconComponent(
                painter = painterResource(
                    resource = if (passwordVisibility) {
                        Res.drawable.ic_eye_off
                    } else {
                        Res.drawable.ic_eye
                    }
                ),
                contentDescription = stringResource(
                    resource = if (passwordVisibility) {
                        Res.string.hide_password
                    } else {
                        Res.string.show_password
                    }
                ),
                onClick = onVisibilityChange
            )
        },
        supportingText = if (errorMessage != null) {
            {
                TextComponent(text = errorMessage)
            }
        } else {
            null
        },
        isError = isError,
        keyboardOptions = KeyboardOptions().copy(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focus.clearFocus()
            }
        ),
        singleLine = true,
        visualTransformation = if (passwordVisibility) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        }
    )
}
