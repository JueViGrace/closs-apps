package org.closs.core.presentation.shared.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.closs.core.presentation.shared.ui.components.display.LetterComponent
import org.closs.core.presentation.shared.utils.calculateIconButtonSize

@Composable
fun AccountButton(
    modifier: Modifier = Modifier
        .padding(horizontal = 8.dp)
        .calculateIconButtonSize(),
    letter: String = "C",
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier.background(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = CircleShape
        )
            .clip(CircleShape),
        onClick = onClick
    ) {
        LetterComponent(
            letter = letter
        )
    }
}
