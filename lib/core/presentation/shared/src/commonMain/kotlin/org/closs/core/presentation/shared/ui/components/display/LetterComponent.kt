package org.closs.core.presentation.shared.ui.components.display

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import org.closs.core.presentation.shared.utils.ScreenSize
import org.closs.core.presentation.shared.utils.getScreenSize

@Composable
fun LetterComponent(
    modifier: Modifier = Modifier,
    letter: String,
) {
    val fontSize: TextUnit = when (getScreenSize()) {
        ScreenSize.Compact -> MaterialTheme.typography.titleMedium.fontSize
        ScreenSize.Medium -> MaterialTheme.typography.titleLarge.fontSize
        ScreenSize.Large -> MaterialTheme.typography.displaySmall.fontSize
    }

    val fontWeight: FontWeight? = when (getScreenSize()) {
        ScreenSize.Compact -> MaterialTheme.typography.titleMedium.fontWeight
        ScreenSize.Medium -> MaterialTheme.typography.titleLarge.fontWeight
        ScreenSize.Large -> MaterialTheme.typography.displaySmall.fontWeight
    }

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        TextComponent(
            text = letter,
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}
