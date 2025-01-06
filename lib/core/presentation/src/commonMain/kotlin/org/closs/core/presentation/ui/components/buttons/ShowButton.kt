package org.closs.core.presentation.ui.components.buttons

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.size
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.closs.core.presentation.ui.components.icons.IconComponent
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.ic_caret_down
import org.closs.core.resources.resources.generated.resources.ic_caret_up
import org.closs.core.resources.resources.generated.resources.show
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ShowButton(
    modifier: Modifier = Modifier,
    state: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        AnimatedContent(
            targetState = state,
        ) {
            IconComponent(
                modifier = Modifier.size(24.dp),
                painter = if (it) {
                    painterResource(Res.drawable.ic_caret_down)
                } else {
                    painterResource(Res.drawable.ic_caret_up)
                },
                contentDescription = stringResource(Res.string.show)
            )
        }
    }
}
