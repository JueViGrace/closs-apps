package org.closs.order.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.presentation.shared.ui.components.icons.IconComponent
import org.closs.core.presentation.shared.utils.ScreenSize
import org.closs.core.presentation.shared.utils.calculateMediumIconSize
import org.closs.core.presentation.shared.utils.getScreenSize
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.customer
import org.closs.core.resources.resources.generated.resources.ic_basket
import org.closs.core.resources.resources.generated.resources.items_quantity
import org.closs.core.resources.resources.generated.resources.route
import org.closs.core.resources.resources.generated.resources.shed
import org.closs.core.resources.resources.generated.resources.status
import org.closs.core.types.order.Order
import org.closs.core.types.shared.common.calculateOrderStatus
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

// todo: add almacen
@Composable
fun OrderListItem(
    order: Order,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
        .clickable { onClick() }
        .fillMaxWidth()
        .padding(4.dp),
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        IconComponent(
            modifier = Modifier
                .zIndex(1f)
                .size(calculateMediumIconSize())
                .align(Alignment.CenterStart),
            painter = painterResource(Res.drawable.ic_basket),
        )

        val cardPadding = when (getScreenSize()) {
            ScreenSize.Compact -> 26.dp
            ScreenSize.Medium -> 34.dp
            ScreenSize.Large -> 44.dp
        }

        OutlinedCard(
            modifier = Modifier
                .padding(start = cardPadding)
                .align(Alignment.CenterEnd),
            colors = CardDefaults.outlinedCardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val rowPadding = when (getScreenSize()) {
                    ScreenSize.Compact -> 16.dp
                    ScreenSize.Medium -> 24.dp
                    ScreenSize.Large -> 34.dp
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = rowPadding),
                    horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextComponent(
                        modifier = Modifier.requiredWidth(IntrinsicSize.Min),
                        text = "${stringResource(Res.string.customer)}:"
                    )
                    TextComponent(
                        modifier = Modifier.weight(1f),
                        text = order.nombrecli,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    TextComponent(
                        modifier = Modifier.requiredWidth(IntrinsicSize.Min),
                        text = "(${order.codcliente})",
                        maxLines = 1,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = rowPadding),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.requiredWidth(IntrinsicSize.Max),
                        horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextComponent(
                            text = "Nº",
                            maxLines = 1
                        )

                        TextComponent(
                            text = order.documento,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }

                    Row(
                        modifier = Modifier
                            .requiredWidth(IntrinsicSize.Max)
                            .padding(horizontal = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextComponent(
                            text = "${stringResource(Res.string.shed)}:",
                            maxLines = 1
                        )

                        TextComponent(
                            text = order.almacen,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextComponent(
                            text = "${stringResource(Res.string.route)}:",
                            maxLines = 1
                        )

                        TextComponent(
                            text = order.rutaDescrip,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = rowPadding),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextComponent(
                            text = "${stringResource(Res.string.items_quantity)}:"
                        )
                        TextComponent(
                            text = "${order.lines.size}",
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextComponent(
                            text = "${stringResource(Res.string.status)}:"
                        )
                        TextComponent(
                            text = stringResource(order.kePedStatus.calculateOrderStatus()),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
