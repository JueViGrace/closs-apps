package org.closs.core.presentation.shared.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getScreenWidth(): Dp {
    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current
    return with(density) {
        windowInfo.containerSize.width.toDp()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
actual fun getScreenHeight(): Dp {
    val windowInfo = LocalWindowInfo.current
    val density = LocalDensity.current
    return with(density) {
        windowInfo.containerSize.height.toDp()
    }
}

@Composable
actual fun getScreenOrientation(): Orientation {
    return if (getScreenWidth() > getScreenHeight()) Orientation.Landscape else Orientation.Portrait
}