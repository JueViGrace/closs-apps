package org.closs.picking.app.presentation.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.NavigationStack
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.presentation.shared.ui.components.icons.IconComponent
import org.closs.core.presentation.shared.utils.calculateDefaultIconSize
import org.closs.core.presentation.shared.utils.calculateFABSize
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.ic_edit
import org.closs.shared.app.presentation.viewmodel.AppViewModel
import org.jetbrains.compose.resources.painterResource

@Composable
fun FABComponent(
    viewModel: AppViewModel,
    navigator: Navigator,
    stack: NavigationStack
) {
    when (stack.currentDestination) {
        // todo: make actions for button
        is Destination.OrderDetails -> {
            FloatingActionButton(
                modifier = Modifier.size(calculateFABSize()),
                shape = CircleShape,
                onClick = {
                }
            ) {
                IconComponent(
                    modifier = Modifier.size(calculateDefaultIconSize()),
                    painter = painterResource(Res.drawable.ic_edit),
                )
            }
        }
        is Destination.PickUp -> {
        }
        null -> TODO()
        else -> {}
    }
}
