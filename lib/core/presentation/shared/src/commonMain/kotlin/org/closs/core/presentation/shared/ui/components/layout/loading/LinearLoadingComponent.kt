package org.closs.core.presentation.shared.ui.components.layout.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun LinearLoadingComponent(
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        LinearProgressIndicator(
            modifier = modifier
        )
    }
}
