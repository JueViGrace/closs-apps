package org.closs.accloss.home.presentation.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import org.closs.core.presentation.shared.ui.components.dialogs.HomeDialog
import org.closs.core.presentation.shared.ui.components.dialogs.content.AccountDialogSection
import org.closs.core.presentation.shared.ui.components.dialogs.content.LogOutDialogItem
import org.closs.core.presentation.shared.ui.components.dialogs.content.NotificationsDialogItem
import org.closs.core.presentation.shared.ui.components.dialogs.content.SettingsDialogItem
import org.closs.core.presentation.shared.ui.components.dialogs.content.SyncDialogItem
import org.closs.core.presentation.shared.ui.components.icons.IconComponent
import org.closs.core.presentation.shared.utils.calculateIconSize
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.ic_refresh
import org.closs.core.resources.resources.generated.resources.sync
import org.closs.shared.home.presentation.events.HomeEvents
import org.closs.shared.home.presentation.state.HomeState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ACHomeDialog(
    state: HomeState,
    onEvent: (HomeEvents) -> Unit,
) {
    HomeDialog(
        onDismiss = {
            onEvent(HomeEvents.ToggleDialog)
        },
        accountSection = {
            state.session?.let { session ->
                AccountDialogSection(
                    modifier = Modifier.padding(8.dp),
                    session = session,
                    onNavigateToProfile = {
                        onEvent(HomeEvents.NavigateToProfile)
                    },
                    hasMultiAccount = {
                        // todo: accounts button
                    }
                )
            }
            // todo: accounts list
        },
        optionsSection = {
            NotificationsDialogItem {
                onEvent(HomeEvents.NavigateToSettings)
            }
            SyncDialogItem(
                onClick = {
                    onEvent(HomeEvents.Sync)
                }
            ) {
                AnimatedVisibility(visible = state.isSyncing) {
                    val infiniteTransition = rememberInfiniteTransition(label = "spin")
                    val angle by infiniteTransition.animateFloat(
                        initialValue = 0f,
                        targetValue = 360f,
                        animationSpec = infiniteRepeatable(
                            animation = tween(1000, easing = LinearEasing),
                            repeatMode = RepeatMode.Restart
                        ),
                        label = "spin"
                    )

                    IconComponent(
                        modifier = Modifier.calculateIconSize().rotate(angle),
                        painter = painterResource(Res.drawable.ic_refresh),
                        contentDescription = stringResource(Res.string.sync),
                    )
                }
            }
        },
        infoSection = {
            SettingsDialogItem {
                onEvent(HomeEvents.NavigateToSettings)
            }
            LogOutDialogItem {
                onEvent(HomeEvents.LogOut)
            }
        }
    )
}
