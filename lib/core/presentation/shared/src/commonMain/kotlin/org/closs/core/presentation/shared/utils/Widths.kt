package org.closs.core.presentation.shared.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import kotlin.math.roundToInt

@Composable
fun calculateMaxDialogSize(): Dp {
    val density = LocalDensity.current
    return with(density) {
        when (getScreenSize()) {
            ScreenSize.Compact -> {
                getScreenWidth()
            }
            ScreenSize.Medium -> {
                (getScreenWidth().toPx() / 1.4).roundToInt().toDp()
            }
            ScreenSize.Large -> {
                (getScreenWidth().toPx() / 2).roundToInt().toDp()
            }
        }
    }
}
