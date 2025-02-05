package org.closs.core.presentation.shared.actions

import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.ic_package_export
import org.closs.core.resources.resources.generated.resources.pick_up
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource

sealed class FABActions(val title: StringResource, val icon: DrawableResource) {
    data object PickUp : FABActions(
        title = Res.string.pick_up,
        icon = Res.drawable.ic_package_export
    )
}
