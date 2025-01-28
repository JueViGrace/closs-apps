package org.closs.order.presentation.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.presentation.shared.ui.components.icons.IconComponent
import org.closs.core.presentation.shared.utils.calculateIconSize
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.ic_eye
import org.closs.core.types.order.Order
import org.jetbrains.compose.resources.painterResource

@Composable
fun OrderListItem(
    order: Order,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
        .clickable { onClick() }
        .fillMaxWidth()
        .padding(12.dp),
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        IconComponent(
            modifier = Modifier.calculateIconSize().align(Alignment.CenterStart),
            painter = painterResource(Res.drawable.ic_eye),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterEnd)
                .padding(start = 12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextComponent(
                text = order.documento
            )
            TextComponent(
                text = order.emision
            )
        }
    }
}
