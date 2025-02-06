package org.closs.order.pickup.presentation.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.presentation.shared.ui.components.icons.IconComponent
import org.closs.core.presentation.shared.utils.calculateDefaultIconSize
import org.closs.core.presentation.shared.utils.calculateLargeFontSize
import org.closs.core.presentation.shared.utils.calculateLargeFontWeight
import org.closs.core.presentation.shared.utils.calculateMaxDialogSize
import org.closs.core.presentation.shared.utils.calculateMediumFontSize
import org.closs.core.presentation.shared.utils.calculateMediumFontWeight
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.cancel
import org.closs.core.resources.resources.generated.resources.cancel_pick_up
import org.closs.core.resources.resources.generated.resources.cancel_pick_up_desc
import org.closs.core.resources.resources.generated.resources.confirm
import org.closs.core.resources.resources.generated.resources.ic_cancel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun PickUpCancelDialog(
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.widthIn(max = calculateMaxDialogSize()),
        onDismissRequest = onCancel,
        confirmButton = {
            ElevatedButton(
                onClick = onConfirm,
                colors = ButtonDefaults.elevatedButtonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                )
            ) {
                TextComponent(
                    text = stringResource(Res.string.confirm)
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onCancel
            ) {
                TextComponent(
                    text = stringResource(Res.string.cancel)
                )
            }
        },
        icon = {
            IconComponent(
                modifier = Modifier.size(calculateDefaultIconSize()),
                painter = painterResource(Res.drawable.ic_cancel),
                contentDescription = stringResource(Res.string.cancel_pick_up),
                tint = MaterialTheme.colorScheme.error
            )
        },
        title = {
            TextComponent(
                text = stringResource(Res.string.cancel_pick_up),
                fontSize = calculateLargeFontSize(),
                fontWeight = calculateLargeFontWeight(),
            )
        },
        text = {
            TextComponent(
                text = stringResource(Res.string.cancel_pick_up_desc),
                fontSize = calculateMediumFontSize(),
                fontWeight = calculateMediumFontWeight(),
            )
        },
        titleContentColor = MaterialTheme.colorScheme.error,
    )
}
