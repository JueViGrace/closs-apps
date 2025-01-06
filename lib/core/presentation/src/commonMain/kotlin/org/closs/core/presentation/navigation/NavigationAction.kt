package org.closs.core.presentation.navigation

import androidx.navigation.NavOptions

sealed interface NavigationAction {
    data class Navigate(
        val destination: org.closs.core.presentation.navigation.Destination,
        val navOptions: NavOptions? = null
    ) : NavigationAction

    data object NavigateUp : NavigationAction
}
