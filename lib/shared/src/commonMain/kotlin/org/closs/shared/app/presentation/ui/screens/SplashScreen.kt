package org.closs.shared.app.presentation.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import org.closs.core.presentation.shared.ui.components.display.ImageComponent
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.ic_avlogo
import org.jetbrains.compose.resources.painterResource

@Composable
fun SplashScreen(
    painter: Painter = painterResource(Res.drawable.ic_avlogo)
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        ImageComponent(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .size(200.dp)
                .padding(4.dp),
            painter = painter,
            contentDescription = "Logo"
        )
    }
}
