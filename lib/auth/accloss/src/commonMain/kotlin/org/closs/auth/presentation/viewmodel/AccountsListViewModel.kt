package org.closs.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.closs.auth.shared.data.AuthRepository
import org.closs.auth.shared.presentation.events.AccountsListEvents
import org.closs.auth.shared.presentation.state.AccountsListState
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.types.shared.state.RequestState

class AccountsListViewModel(
    private val navigator: Navigator,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AccountsListState())
    val state = combine(
        _state,
        authRepository.getAccounts()
    ) { state, accounts ->
        when (accounts) {
            is RequestState.Error -> {
                state.copy(
                    accounts = emptyList(),
                    isLoading = false
                )
            }
            is RequestState.Success -> {
                state.copy(
                    accounts = accounts.data,
                    isLoading = false
                )
            }
            else -> {
                state.copy(
                    accounts = emptyList(),
                    isLoading = true
                )
            }
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        AccountsListState()
    )

    fun onEvent(event: AccountsListEvents) {
        when (event) {
            is AccountsListEvents.OnAccountClick -> accountClick(event.id)
            AccountsListEvents.OnSignInNavigate -> signInNavigate()
        }
    }

    private fun accountClick(id: String) {
        viewModelScope.launch {
            authRepository.startSession(id)
        }
    }

    private fun signInNavigate() {
        viewModelScope.launch {
            navigator.navigate(
                destination = Destination.SignIn,
                navOptions = NavOptions.Builder().apply {
                    setPopUpTo(route = Destination.Accounts, inclusive = false)
                    setLaunchSingleTop(true)
                }.build()
            )
        }
    }
}
