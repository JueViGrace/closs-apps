package org.closs.order.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.presentation.shared.utils.calculateLabelFontSize
import org.closs.core.presentation.shared.utils.calculateLabelFontWeight
import org.closs.core.presentation.shared.utils.calculateMediumFontSize
import org.closs.core.presentation.shared.utils.calculateMediumFontWeight
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.customer
import org.closs.core.resources.resources.generated.resources.emitted
import org.closs.core.resources.resources.generated.resources.route
import org.closs.core.resources.resources.generated.resources.status
import org.closs.core.types.order.Order
import org.closs.core.types.shared.common.calculateOrderStatus
import org.jetbrains.compose.resources.stringResource

@Composable
fun OrderHeadComponent(
    order: Order
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TextComponent(
                    text = order.tipodoc,
                    color = MaterialTheme.colorScheme.onSurface.copy(0.6f),
                    maxLines = 1,
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TextComponent(
                modifier = Modifier.requiredWidth(IntrinsicSize.Min),
                text = "${stringResource(Res.string.customer)}:",
                maxLines = 1
            )
            TextComponent(
                modifier = Modifier.weight(1f),
                text = "${order.nombrecli} (${order.codcliente})",
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextComponent(
                    text = "${stringResource(Res.string.emitted)}:",
                    maxLines = 1
                )
                TextComponent(
                    text = order.emision,
                    maxLines = 1
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextComponent(
                    text = "${stringResource(Res.string.status)}:",
                    maxLines = 1
                )
                TextComponent(
                    text = stringResource(order.kePedStatus.calculateOrderStatus()),
                    maxLines = 1
                )
            }
        }
    }
}
