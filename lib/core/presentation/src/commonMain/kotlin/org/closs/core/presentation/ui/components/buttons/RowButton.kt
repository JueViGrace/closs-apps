package org.closs.core.presentation.ui.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import org.closs.core.presentation.ui.components.display.RowComponent

@Composable
fun RowButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(10),
    colors: ButtonColors = ButtonDefaults.elevatedButtonColors(),
    elevation: ButtonElevation? = ButtonDefaults.elevatedButtonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource? = null,
    icon: @Composable RowScope.() -> Unit = {},
    title: @Composable RowScope.() -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
) {
    ElevatedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = elevation,
        border = border,
        contentPadding = contentPadding,
        interactionSource = interactionSource
    ) {
        RowComponent(
            icon = icon,
            title = title,
            actions = actions,
            horizontalArrangement = Arrangement.SpaceEvenly
        )
    }
}