package org.closs.core.presentation.shared.utils

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
actual fun getScreenWidth(): Dp {
    val windowInfo = LocalConfiguration.current
    val density = LocalDensity.current
    return windowInfo.screenWidthDp.dp
}

@Composable
actual fun getScreenHeight(): Dp {
    val windowInfo = LocalConfiguration.current
    val density = LocalDensity.current
    return windowInfo.screenHeightDp.dp
}

@Composable
actual fun getScreenOrientation(): Orientation {
    var orientation by rememberSaveable { mutableIntStateOf(Configuration.ORIENTATION_PORTRAIT) }
    val configuration = LocalConfiguration.current

    LaunchedEffect(configuration) {
        snapshotFlow { configuration.orientation }
            .collect { orientation = it }
    }

    return if (orientation == Configuration.ORIENTATION_LANDSCAPE) Orientation.Landscape else Orientation.Portrait
}
