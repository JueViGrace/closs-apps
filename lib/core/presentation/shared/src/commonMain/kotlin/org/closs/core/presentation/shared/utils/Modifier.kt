package org.closs.core.presentation.shared.utils

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.calculateIconButtonSize(): Modifier {
    val size: Dp = when (getScreenSize()) {
        ScreenSize.Compact -> 30.dp
        ScreenSize.Medium -> 48.dp
        ScreenSize.Large -> 62.dp
    }

    return this.size(size)
}

@Composable
fun Modifier.calculateIconSize(): Modifier {
    val size: Dp = when (getScreenSize()) {
        ScreenSize.Compact -> 20.dp
        ScreenSize.Medium -> 34.dp
        ScreenSize.Large -> 48.dp
    }

    return this.size(size)
}
