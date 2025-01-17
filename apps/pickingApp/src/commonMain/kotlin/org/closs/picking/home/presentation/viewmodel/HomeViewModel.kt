package org.closs.picking.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import kotlinx.coroutines.launch
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.Navigator

class HomeViewModel(
    private val navigator: Navigator,
) : ViewModel() {
    fun navigateToPickingHistory() {
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.PickingHistory,
                navOptions = NavOptions.Builder().apply {
                    setPopUpTo(Destination.Home, false)
                }.build()
            )
        }
    }

    fun navigateToPendingOrders() {
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.PendingOrders,
                navOptions = NavOptions.Builder().apply {
                    setPopUpTo(Destination.Home, false)
                }.build()
            )
        }
    }
}
