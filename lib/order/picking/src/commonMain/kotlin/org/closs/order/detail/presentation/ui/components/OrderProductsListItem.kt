package org.closs.order.detail.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.LocalPlatformContext
import coil3.compose.SubcomposeAsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.closs.core.presentation.shared.ui.components.display.ImageComponent
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.presentation.shared.utils.ScreenSize
import org.closs.core.presentation.shared.utils.calculateDefaultImageSize
import org.closs.core.presentation.shared.utils.calculateLabelFontSize
import org.closs.core.presentation.shared.utils.calculateLabelFontWeight
import org.closs.core.presentation.shared.utils.calculateMediumFontSize
import org.closs.core.presentation.shared.utils.calculateMediumFontWeight
import org.closs.core.presentation.shared.utils.getScreenSize
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.ic_photo_x
import org.closs.core.resources.resources.generated.resources.ordered_quantity
import org.closs.core.resources.resources.generated.resources.product_image
import org.closs.core.types.order.OrderLine
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

// todo: add product details if needed
@Composable
fun OrderProductsListItem(
    modifier: Modifier = Modifier,
    orderLine: OrderLine,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            // todo:create component and observer state to avoid using this composable
            SubcomposeAsyncImage(
                modifier = Modifier
                    .zIndex(1f)
                    .align(Alignment.CenterStart)
                    .size(calculateDefaultImageSize())
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(10)
                    )
                    .clip(RoundedCornerShape(10))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                        shape = RoundedCornerShape(10)
                    ),
                model = ImageRequest.Builder(LocalPlatformContext.current)
                    .data(orderLine.product.image)
                    .crossfade(true)
                    .build(),
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier.padding(4.dp)
                    )
                },
                error = {
                    ImageComponent(
                        modifier = Modifier
                            .size(calculateDefaultImageSize())
                            .background(
                                color = MaterialTheme.colorScheme.surfaceContainer,
                                shape = RoundedCornerShape(10)
                            )
                            .clip(RoundedCornerShape(10))
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(10)
                            )
                            .padding(4.dp),
                        painter = painterResource(Res.drawable.ic_photo_x),
                        contentDescription = stringResource(Res.string.product_image),
                        contentScale = contentScale,
                    )
                },
                success = {
                    ImageComponent(
                        modifier = Modifier
                            .size(calculateDefaultImageSize())
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(10)
                            )
                            .clip(RoundedCornerShape(10))
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outlineVariant,
                                shape = RoundedCornerShape(10)
                            )
                            .padding(4.dp),
                        painter = painter,
                        contentDescription = contentDescription,
                        contentScale = contentScale,
                    )
                },
                contentDescription = orderLine.product.nombre,
            )

            val cardPadding = when (getScreenSize()) {
                ScreenSize.Compact -> 54.dp
                ScreenSize.Medium -> 59.dp
                ScreenSize.Large -> 64.dp
            }

            Column(
                modifier = Modifier
                    .padding(start = cardPadding)
                    .align(Alignment.CenterEnd),
            ) {
                val columnStartPadding = when (getScreenSize()) {
                    ScreenSize.Compact -> 58.dp
                    ScreenSize.Medium -> 68.dp
                    ScreenSize.Large -> 78.dp
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = columnStartPadding,
                            end = 12.dp,
                            top = 12.dp,
                            bottom = 12.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.weight(1f),
                            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.Start),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TextComponent(
                                text = orderLine.product.codigo,
                                fontSize = calculateMediumFontSize(),
                                fontWeight = calculateMediumFontWeight(),
                            )
                            TextComponent(
                                text = "(${orderLine.product.referencia})",
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                            )
                        }
                        TextComponent(
                            modifier = Modifier.requiredWidth(IntrinsicSize.Min),
                            text = orderLine.product.marca,
                            fontSize = calculateLabelFontSize(),
                            fontWeight = calculateLabelFontWeight(),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                    TextComponent(
                        modifier = Modifier.fillMaxWidth(),
                        text = orderLine.product.nombre,
                        textAlign = TextAlign.Start,
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextComponent(
                            text = "${stringResource(Res.string.ordered_quantity)}:",
                        )
                        TextComponent(
                            text = orderLine.cantref.toString(),
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }

        HorizontalDivider()
    }
}
