package org.closs.core.presentation.ui.components.buttons

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import org.closs.core.presentation.ui.components.display.LetterComponent

@Composable
fun AccountButton(
    letter: String = "C",
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick
    ) {
        LetterComponent(
            letter = letter
        )
    }
}
