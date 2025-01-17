package org.closs.core.presentation.shared.utils

import androidx.compose.foundation.layout.sizeIn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.calculateIconButtonSize(): Modifier {
    println(getScreenSize())
    val maxSize: Dp = when (getScreenSize()) {
        ScreenSize.Compact -> 38.dp
        ScreenSize.Medium -> 68.dp
        ScreenSize.Large -> 98.dp
    }

    return this.sizeIn(
        minWidth = 32.dp,
        minHeight = 32.dp,
        maxWidth = maxSize,
        maxHeight = maxSize
    )
}
