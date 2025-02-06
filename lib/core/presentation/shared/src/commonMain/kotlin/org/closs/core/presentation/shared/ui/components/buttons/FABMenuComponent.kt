package org.closs.core.presentation.shared.ui.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import org.closs.core.presentation.shared.actions.FABActions
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.presentation.shared.ui.components.icons.IconComponent
import org.closs.core.presentation.shared.utils.calculateDefaultIconSize
import org.closs.core.presentation.shared.utils.calculateFABSize
import org.closs.core.presentation.shared.utils.calculateMediumFontSize
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.ic_edit
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun FABMenuComponent(
    showMenu: Boolean = false,
    toggleMenu: () -> Unit,
    icon: Painter = painterResource(Res.drawable.ic_edit),
    actions: List<FABActions> = emptyList(),
    onActionClick: (FABActions) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.Bottom),
        horizontalAlignment = Alignment.End
    ) {
        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = toggleMenu,
            containerColor = Color.Transparent,
            tonalElevation = 0.dp,
            shadowElevation = 0.dp,
            border = null
        ) {
            for (action in actions) {
                DropdownMenuItem(
                    onClick = {
                        onActionClick(action)
                    },
                    text = {
                        ElevatedCard(
                            shape = RoundedCornerShape(5.dp),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceContainer
                            ),
                        ) {
                            TextComponent(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                text = stringResource(action.title),
                                fontSize = calculateMediumFontSize(),
                            )
                        }
                    },
                    trailingIcon = {
                        IconComponent(
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceContainer,
                                    shape = CircleShape
                                )
                                .clip(CircleShape)
                                .padding(8.dp)
                                .size(calculateDefaultIconSize()),
                            painter = painterResource(action.icon),
                            contentDescription = stringResource(action.title),
                        )
                    }
                )
            }
        }

        FloatingActionButton(
            modifier = Modifier.size(calculateFABSize()),
            shape = CircleShape,
            onClick = {
                toggleMenu()
            },
        ) {
            IconComponent(
                modifier = Modifier.size(calculateDefaultIconSize()),
                painter = icon,
            )
        }
    }
}
