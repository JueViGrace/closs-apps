package org.closs.core.presentation.shared.ui.components.layout.bars

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import org.closs.core.presentation.shared.utils.ScreenSize
import org.closs.core.presentation.shared.utils.getScreenSize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarComponent(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
    expandedHeight: Dp = calculateTopBarHeight(),
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = title,
        actions = actions,
        expandedHeight = expandedHeight,
        navigationIcon = navigationIcon,
        colors = colors,
        scrollBehavior = scrollBehavior
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun calculateTopBarHeight(): Dp {
    return when (getScreenSize()) {
        ScreenSize.Compact -> TopAppBarDefaults.TopAppBarExpandedHeight
        ScreenSize.Medium -> TopAppBarDefaults.MediumAppBarExpandedHeight
        ScreenSize.Large -> TopAppBarDefaults.LargeAppBarExpandedHeight
    }
}
