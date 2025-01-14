package org.closs.core.presentation.shared.navigation

import androidx.navigation.NavOptions

sealed interface NavigationAction {
    data class Navigate(
        val destination: Destination,
        val navOptions: NavOptions? = null
    ) : NavigationAction

    data object NavigateUp : NavigationAction
}
