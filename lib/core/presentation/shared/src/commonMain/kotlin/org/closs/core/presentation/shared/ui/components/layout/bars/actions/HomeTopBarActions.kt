package org.closs.core.presentation.shared.ui.components.layout.bars.actions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.closs.core.presentation.shared.ui.components.buttons.AccountButton
import org.closs.core.presentation.shared.ui.components.icons.IconComponent
import org.closs.core.presentation.shared.utils.calculateIconButtonSize
import org.closs.core.presentation.shared.utils.calculateIconSize
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.ic_bell
import org.closs.core.resources.resources.generated.resources.notifications
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun HomeTopBarActions(
    accountLetter: String,
    onNotificationsClick: () -> Unit,
    onAccountClick: () -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .calculateIconButtonSize()
                .clip(CircleShape)
                .clickable {
                    onNotificationsClick()
                },
            contentAlignment = Alignment.Center
        ) {
            IconComponent(
                modifier = Modifier.calculateIconSize(),
                painter = painterResource(Res.drawable.ic_bell),
                contentDescription = stringResource(Res.string.notifications),
            )
        }

        AccountButton(
            letter = accountLetter,
            onClick = onAccountClick
        )
    }
}
