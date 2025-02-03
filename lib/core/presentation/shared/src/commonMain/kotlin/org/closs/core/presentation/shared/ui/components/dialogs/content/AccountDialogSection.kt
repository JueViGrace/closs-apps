package org.closs.core.presentation.shared.ui.components.dialogs.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.closs.core.presentation.shared.ui.components.display.LetterComponent
import org.closs.core.presentation.shared.ui.components.display.RowComponent
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.presentation.shared.utils.calculateIconButtonSize
import org.closs.core.presentation.shared.utils.calculateLabelFontSize
import org.closs.core.presentation.shared.utils.calculateLabelFontWeight
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.your_account
import org.closs.core.types.shared.auth.Session
import org.jetbrains.compose.resources.stringResource

@Composable
fun AccountDialogSection(
    modifier: Modifier = Modifier,
    session: Session,
    onNavigateToProfile: () -> Unit,
    hasMultiAccount: (@Composable () -> Unit)? = null
) {
    RowComponent(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top,
        icon = {
            Box(
                modifier = Modifier
                    .size(calculateIconButtonSize())
                    .background(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = CircleShape
                    )
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                LetterComponent(
                    letter = session.name.firstOrNull()?.toString() ?: "P"
                )
            }
        },
        title = {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.SpaceEvenly,
                        horizontalAlignment = Alignment.Start
                    ) {
                        TextComponent(
                            text = session.name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        TextComponent(
                            text = session.user?.username ?: "",
                            fontSize = calculateLabelFontSize(),
                            fontWeight = calculateLabelFontWeight(),
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }

                    hasMultiAccount?.let { fn ->
                        fn()
                    }
                }

                OutlinedButton(
                    onClick = onNavigateToProfile,
                    shape = RoundedCornerShape(18),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 2.dp),
                ) {
                    TextComponent(
                        text = stringResource(Res.string.your_account)
                    )
                }
            }
        }
    )
}
