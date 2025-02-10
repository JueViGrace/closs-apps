package org.closs.order.presentation.components

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
import org.closs.core.resources.resources.generated.resources.ordered
import org.closs.core.resources.resources.generated.resources.product_image
import org.closs.core.resources.resources.generated.resources.stock
import org.closs.core.types.order.OrderLine
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun OrderProductItem(
    modifier: Modifier = Modifier,
    line: OrderLine,
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        // todo:create component and observer state to avoid using this composable
        // todo: also create zoom effect or dialog to see image more clearly
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
                .data(line.product.image)
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
            contentDescription = line.product.nombre,
        )

        val cardPadding = when (getScreenSize()) {
            ScreenSize.Compact -> 50.dp
            ScreenSize.Medium -> 55.dp
            ScreenSize.Large -> 60.dp
        }

        Column(
            modifier = Modifier
                .padding(start = cardPadding)
                .align(Alignment.CenterEnd),
        ) {
            val columnStartPadding = when (getScreenSize()) {
                ScreenSize.Compact -> 55.dp
                ScreenSize.Medium -> 65.dp
                ScreenSize.Large -> 75.dp
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
                            text = line.product.codigo,
                            fontSize = calculateMediumFontSize(),
                            fontWeight = calculateMediumFontWeight(),
                        )
                        TextComponent(
                            text = "(${line.product.referencia})",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                    TextComponent(
                        modifier = Modifier.requiredWidth(IntrinsicSize.Min),
                        text = line.product.marca,
                        fontSize = calculateLabelFontSize(),
                        fontWeight = calculateLabelFontWeight(),
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
                TextComponent(
                    modifier = Modifier.fillMaxWidth(),
                    text = line.product.nombre,
                    textAlign = TextAlign.Start,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextComponent(
                            text = "${stringResource(Res.string.ordered)}:",
                        )
                        TextComponent(
                            text = line.cantref.toString(),
                            fontWeight = FontWeight.Bold,
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.Start),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TextComponent(
                            text = "${stringResource(Res.string.stock)}:",
                        )
                        TextComponent(
                            text = line.product.existencia.toString(),
                            fontWeight = FontWeight.Bold,
                        )
                    }
                }
            }
        }
    }
}
