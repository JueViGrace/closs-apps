package org.closs.core.presentation.ui.components.navigation

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.closs.core.presentation.ui.components.display.TextComponent

@Composable
fun DrawerNavItem(
    modifier: Modifier,
    title: String,
    icon: @Composable () -> Unit,
    selected: Boolean,
    onSelect: () -> Unit
) {
    NavigationDrawerItem(
        modifier = modifier,
        selected = selected,
        onClick = {
            onSelect()
        },
        icon = icon,
        label = { TextComponent(text = title) },
        shape = RoundedCornerShape(8.dp)
    )
}