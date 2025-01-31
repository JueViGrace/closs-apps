package org.closs.order.detail.presentation.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.presentation.shared.utils.calculateLabelFontSize
import org.closs.core.presentation.shared.utils.calculateLabelFontWeight
import org.closs.core.presentation.shared.utils.calculateMediumFontSize
import org.closs.core.presentation.shared.utils.calculateMediumFontWeight
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.route
import org.closs.core.types.order.Order
import org.jetbrains.compose.resources.stringResource

@Composable
fun OrderHeadComponent(
    order: Order
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.Start),
            ) {
                TextComponent(
                    text = order.tipodoc,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
                    maxLines = 1
                )
                TextComponent(
                    text = "Nº ${order.documento}",
                    fontSize = calculateMediumFontSize(),
                    fontWeight = calculateMediumFontWeight(),
                    maxLines = 1
                )
            }

            TextComponent(
                modifier = Modifier.requiredWidth(IntrinsicSize.Max),
                text = "${stringResource(Res.string.route)} ${order.rutaDescrip}",
                fontSize = calculateLabelFontSize(),
                fontWeight = calculateLabelFontWeight(),
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                maxLines = 1
            )
        }
    }
}
