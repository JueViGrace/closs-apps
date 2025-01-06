package org.closs.core.presentation.ui.components.buttons

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import org.closs.core.presentation.ui.components.icons.IconComponent
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.go_back
import org.closs.core.resources.resources.generated.resources.ic_chevron_left
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun BackArrowButton(
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick
    ) {
        IconComponent(
            painter = painterResource(Res.drawable.ic_chevron_left),
            contentDescription = stringResource(Res.string.go_back)
        )
    }
}
