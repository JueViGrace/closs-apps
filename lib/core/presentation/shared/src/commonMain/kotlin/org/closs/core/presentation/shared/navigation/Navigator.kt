package org.closs.core.presentation.shared.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavOptions
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update

interface Navigator {
    val startDestination: Destination
    val navigationActions: Flow<NavigationAction>
    val stack: StateFlow<NavigationStack>
    val stateHandle: SavedStateHandle

    suspend fun navigate(
        destination: Destination,
        navOptions: NavOptions? = null
    )

    suspend fun navigateUp()
}

class DefaultNavigator(
    override val startDestination: Destination,
    override val stateHandle: SavedStateHandle
) : Navigator {
    private val _navigationActions = Channel<NavigationAction>()
    override val navigationActions: Flow<NavigationAction> = _navigationActions.receiveAsFlow()

    private val _stack = MutableStateFlow(NavigationStack(mutableListOf(startDestination), startDestination))
    override val stack: StateFlow<NavigationStack> = _stack.asStateFlow()

    override suspend fun navigate(
        destination: Destination,
        navOptions: NavOptions?
    ) {
        _stack.update { stack ->
            if (navOptions?.isPopUpToInclusive() == true) {
                stack.destinations.remove(navOptions.popUpToRouteObject as Destination)
            }

            if (stack.destinations.contains(destination)) {
                return@update stack
            }

            stack.destinations.add(destination)
            stack.copy(
                currentDestination = stack.destinations.lastOrNull()
            )
        }
        _navigationActions.send(NavigationAction.Navigate(destination, navOptions))
    }

    override suspend fun navigateUp() {
        println("navigateUp")
        println(stack.value.destinations)
        println(stack.value.currentDestination)
        _stack.update { stack ->
            if (stack.destinations.isEmpty()) {
                return@update stack
            }
            stack.destinations.removeLastOrNull()
            stack.copy(
                currentDestination = stack.destinations.lastOrNull()
            )
        }
        _navigationActions.send(NavigationAction.NavigateUp)
    }
}

data class NavigationStack(
    val destinations: MutableList<Destination> = mutableListOf(),
    val currentDestination: Destination? = null,
)
