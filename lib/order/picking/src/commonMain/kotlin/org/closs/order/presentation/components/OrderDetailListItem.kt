package org.closs.order.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.closs.core.types.order.OrderLine

// todo: add product details if needed
@Composable
fun OrderDetailListItem(
    modifier: Modifier = Modifier,
    line: OrderLine,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        OrderProductItem(
            modifier = Modifier.fillMaxWidth(),
            line = line,
        )
        HorizontalDivider()
    }
}
