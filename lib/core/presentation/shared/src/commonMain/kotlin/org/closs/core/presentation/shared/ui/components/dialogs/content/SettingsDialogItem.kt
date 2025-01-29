package org.closs.core.presentation.shared.ui.components.dialogs.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.closs.core.presentation.shared.ui.components.display.RowComponent
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.presentation.shared.ui.components.icons.IconComponent
import org.closs.core.presentation.shared.utils.calculateSmallIconSize
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.ic_settings
import org.closs.core.resources.resources.generated.resources.settings
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SettingsDialogItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    RowComponent(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(8.dp),
        icon = {
            IconComponent(
                modifier = Modifier.calculateSmallIconSize(),
                painter = painterResource(Res.drawable.ic_settings),
                contentDescription = stringResource(Res.string.settings)
            )
        },
        title = {
            TextComponent(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.settings),
            )
        }
    )
}
