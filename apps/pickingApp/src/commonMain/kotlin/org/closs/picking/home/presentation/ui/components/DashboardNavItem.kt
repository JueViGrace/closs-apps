package org.closs.picking.home.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.presentation.shared.ui.components.icons.IconComponent
import org.closs.core.presentation.shared.utils.ScreenSize
import org.closs.core.presentation.shared.utils.getScreenSize
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.navigate_forward
import org.closs.core.resources.resources.generated.resources.picking_dash_image
import org.jetbrains.compose.resources.stringResource

@Composable
fun DashboardNavItem(
    modifier: Modifier = Modifier,
    title: String,
    image: Painter,
    icon: Painter,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconComponent(
            modifier = Modifier
                .size(
                    size = when (getScreenSize()) {
                        ScreenSize.Compact -> 80.dp
                        ScreenSize.Medium -> 120.dp
                        ScreenSize.Large -> 175.dp
                    }
                )
                .weight(1f),
            painter = image,
            contentDescription = stringResource(Res.string.picking_dash_image)
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextComponent(
                text = title,
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                textAlign = TextAlign.Center
            )
            IconComponent(
                modifier = Modifier.size(48.dp),
                painter = icon,
                contentDescription = stringResource(Res.string.navigate_forward)
            )
        }
    }
}
