package org.closs.core.presentation.shared.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
expect fun getScreenWidth(): Dp

@Composable
expect fun getScreenHeight(): Dp

@Composable
expect fun getScreenOrientation(): Orientation

enum class Orientation {
    Portrait,
    Landscape,
}

@Composable
fun getScreenSize(): ScreenSize {
    val width = getScreenWidth()
    val height = getScreenHeight()
    println(width)
    println(height)

    return when (getScreenOrientation()) {
        Orientation.Portrait -> {
            when {
                width < 600.dp ||
                    (height >= 480.dp && height < 900.dp) -> ScreenSize.Compact
                (width >= 600.dp && width < 840.dp) ||
                    (height >= 900.dp) -> ScreenSize.Medium
                width >= 840.dp || height > 900.dp -> ScreenSize.Large
                else -> ScreenSize.Large
            }
        }
        Orientation.Landscape -> {
            when {
                height < 480.dp || width <= 840.dp -> ScreenSize.Compact
                (height >= 480.dp && height < 900.dp) ||
                    width > 840.dp -> ScreenSize.Medium
                height >= 1200.dp || width > 840.dp -> ScreenSize.Large
                else -> ScreenSize.Large
            }
        }
    }
}

enum class ScreenSize {
    Compact,
    Medium,
    Large
}
