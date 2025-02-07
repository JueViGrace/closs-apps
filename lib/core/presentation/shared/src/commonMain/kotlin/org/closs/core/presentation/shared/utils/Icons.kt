package org.closs.core.presentation.shared.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun calculateIconButtonSize(): Dp {
    return when (getScreenSize()) {
        ScreenSize.Compact -> 30.dp
        ScreenSize.Medium -> 48.dp
        ScreenSize.Large -> 62.dp
    }
}

@Composable
fun calculateSmallIconSize(): Dp {
    return when (getScreenSize()) {
        ScreenSize.Compact -> 20.dp
        ScreenSize.Medium -> 34.dp
        ScreenSize.Large -> 48.dp
    }
}

@Composable
fun calculateDefaultIconSize(): Dp {
    return when (getScreenSize()) {
        ScreenSize.Compact -> 24.dp
        ScreenSize.Medium -> 38.dp
        ScreenSize.Large -> 52.dp
    }
}

@Composable
fun calculateMediumIconSize(): Dp {
    return when (getScreenSize()) {
        ScreenSize.Compact -> 52.dp
        ScreenSize.Medium -> 66.dp
        ScreenSize.Large -> 80.dp
    }
}

@Composable
fun calculateDefaultImageSize(): Dp {
    return when (getScreenSize()) {
        ScreenSize.Compact -> 100.dp
        ScreenSize.Medium -> 110.dp
        ScreenSize.Large -> 120.dp
    }
}

@Composable
fun calculateFABSize(): Dp {
    return when (getScreenSize()) {
        ScreenSize.Compact -> 56.dp
        ScreenSize.Medium -> 86.dp
        ScreenSize.Large -> 106.dp
    }
}
