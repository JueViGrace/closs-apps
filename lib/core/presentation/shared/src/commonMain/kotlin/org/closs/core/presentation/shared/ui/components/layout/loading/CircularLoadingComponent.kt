package org.closs.core.presentation.shared.ui.components.layout.loading

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CircularLoadingComponent(
    modifier: Modifier = Modifier.fillMaxWidth()
) {
    Box(
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = modifier
        )
    }
}
