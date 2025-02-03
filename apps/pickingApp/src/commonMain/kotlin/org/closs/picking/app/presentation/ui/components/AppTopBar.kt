package org.closs.picking.app.presentation.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.NavigationStack
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.presentation.shared.ui.components.buttons.BackArrowButton
import org.closs.core.presentation.shared.ui.components.display.TextComponent
import org.closs.core.presentation.shared.ui.components.icons.IconComponent
import org.closs.core.presentation.shared.ui.components.layout.bars.TopBarComponent
import org.closs.core.presentation.shared.ui.components.layout.bars.actions.HomeTopBarActions
import org.closs.core.presentation.shared.utils.calculateSmallIconSize
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.ic_refresh
import org.closs.core.resources.resources.generated.resources.notifications
import org.closs.shared.app.presentation.state.AppState
import org.closs.shared.app.presentation.viewmodel.AppViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    state: AppState,
    viewModel: AppViewModel,
    navigator: Navigator,
    stack: NavigationStack,
) {
    val scope = rememberCoroutineScope()
    when (stack.currentDestination) {
        Destination.ForgotPassword -> {
            TopBarComponent(
                navigationIcon = {
                    BackArrowButton {
                        scope.launch {
                            navigator.navigateUp()
                        }
                    }
                },
            )
        }

        Destination.Home -> {
            TopBarComponent(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                ),
                actions = {
                    HomeTopBarActions(
                        accountLetter = state.session?.name?.firstOrNull()?.toString() ?: "P",
                        onNotificationsClick = {
                            viewModel.navigateToNotifications()
                        },
                        onAccountClick = {
                            viewModel.toggleDialog()
                        }
                    )
                }
            )
        }
        Destination.Profile -> {
            TopBarComponent(
                navigationIcon = {
                    BackArrowButton {
                        scope.launch {
                            navigator.navigateUp()
                        }
                    }
                },
            )
        }
        Destination.Settings -> {
            TopBarComponent(
                navigationIcon = {
                    BackArrowButton {
                        scope.launch {
                            navigator.navigateUp()
                        }
                    }
                },
            )
        }
        Destination.Notifications -> {
            TopBarComponent(
                title = {
                    TextComponent(
                        text = stringResource(Res.string.notifications),
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight,
                    )
                },
                navigationIcon = {
                    BackArrowButton {
                        scope.launch {
                            navigator.navigateUp()
                        }
                    }
                },
            )
        }
        Destination.PickingHistory -> {
            TopBarComponent(
                navigationIcon = {
                    BackArrowButton {
                        scope.launch {
                            navigator.navigateUp()
                        }
                    }
                },
            )
        }
        Destination.Orders -> {
            TopBarComponent(
                navigationIcon = {
                    BackArrowButton {
                        scope.launch {
                            navigator.navigateUp()
                        }
                    }
                },
                actions = {
                    // todo: change for menu actions or add fab to display options
                    // filtering, reload
                    IconComponent(
                        iconModifier = Modifier.size(calculateSmallIconSize()),
                        painter = painterResource(Res.drawable.ic_refresh),
                        onClick = {
                            viewModel.reloadOrders()
                        }
                    )
                },
            )
        }
        is Destination.OrderDetails -> {
            TopBarComponent(
                navigationIcon = {
                    BackArrowButton {
                        scope.launch {
                            navigator.navigateUp()
                        }
                    }
                },
            )
        }
        // todo: disable or create dialog to warn user about leaving the operation
        is Destination.PickUp -> {
            TopBarComponent(
                navigationIcon = {
                    BackArrowButton {
                        scope.launch {
                            navigator.navigateUp()
                        }
                    }
                },
            )
        }
        Destination.Products -> { }
        is Destination.ProductDetails -> { }
        null -> { }
        else -> {}
    }
}
