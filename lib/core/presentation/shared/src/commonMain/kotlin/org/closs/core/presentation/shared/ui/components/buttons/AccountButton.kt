package org.closs.core.presentation.shared.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import org.closs.core.presentation.shared.ui.components.display.LetterComponent
import org.closs.core.presentation.shared.utils.calculateIconButtonSize

@Composable
fun AccountButton(
    modifier: Modifier = Modifier
        .calculateIconButtonSize()
        .background(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = CircleShape,
        )
        .clip(CircleShape),
    letter: String = "C",
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        LetterComponent(
            letter = letter
        )
    }
}
