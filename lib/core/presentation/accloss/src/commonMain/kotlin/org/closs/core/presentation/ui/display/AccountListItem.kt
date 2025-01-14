package org.closs.core.presentation.ui.display

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.closs.core.presentation.shared.ui.components.display.RowComponent
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.presentation.ui.icons.CompanyIconComponent

@Composable
fun AccountListItem(
    modifier: Modifier = Modifier.fillMaxWidth().padding(8.dp),
    company: String,
    title: String,
    subTitle: String
) {
    RowComponent(
        modifier = modifier,
        icon = {
            CompanyIconComponent(
                company = company
            )
        },
        title = {
            Column(
                modifier = Modifier.fillMaxWidth().weight(0.7f).padding(horizontal = 6.dp)
            ) {
                TextComponent(
                    text = title,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontWeight = MaterialTheme.typography.bodyLarge.fontWeight
                )
                TextComponent(
                    text = subTitle,
                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                    fontWeight = MaterialTheme.typography.bodySmall.fontWeight
                )
            }
        }
    )
}
