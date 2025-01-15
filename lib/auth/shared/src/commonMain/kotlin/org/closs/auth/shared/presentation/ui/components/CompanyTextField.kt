package org.closs.auth.shared.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.presentation.shared.ui.components.icons.IconComponent
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.company
import org.closs.core.resources.resources.generated.resources.company_text_field_icon
import org.closs.core.resources.resources.generated.resources.ic_buildings
import org.closs.core.resources.resources.generated.resources.your_company
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CompanyTextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    value: String,
    onValueChange: (String) -> Unit,
    errorMessage: String? = null,
    enabled: Boolean = true,
    isError: Boolean = errorMessage != null,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        enabled = enabled,
        label = {
            TextComponent(text = stringResource(Res.string.company))
        },
        placeholder = {
            TextComponent(text = stringResource(Res.string.your_company))
        },
        leadingIcon = {
            IconComponent(
                painter = painterResource(Res.drawable.ic_buildings),
                contentDescription = stringResource(Res.string.company_text_field_icon)
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
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done,
        ),
        singleLine = true,
    )
}
