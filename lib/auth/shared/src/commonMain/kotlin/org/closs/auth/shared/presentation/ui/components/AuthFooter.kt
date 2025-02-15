package org.closs.auth.shared.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.app_name
import org.closs.core.types.shared.common.Constants
import org.jetbrains.compose.resources.stringResource

@Composable
fun AuthFooter(
    modifier: Modifier = Modifier,
    title: String = stringResource(Res.string.app_name)
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextComponent(
            text = title,
            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
            fontWeight = MaterialTheme.typography.bodyLarge.fontWeight,
        )
        TextComponent(
            text = Constants.APP_VERSION,
            fontSize = MaterialTheme.typography.labelMedium.fontSize,
            fontWeight = MaterialTheme.typography.labelMedium.fontWeight,
        )
    }
}
