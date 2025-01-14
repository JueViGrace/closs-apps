package org.closs.auth.shared.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.closs.auth.shared.data.repository.AuthRepository
import org.closs.auth.shared.presentation.events.AccountsListEvents
import org.closs.auth.shared.presentation.state.AccountsListState
import org.closs.core.presentation.shared.messages.Messages
import org.closs.core.presentation.shared.navigation.Destination
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.welcome_back
import org.closs.core.types.shared.state.DataCodes
import org.closs.core.types.shared.state.RequestState

class AccountsListViewModel(
    private val navigator: Navigator,
    messages: Messages,
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
                messages.sendMessage(
                    DataCodes.CustomMessage(
                        msg = Res.string.welcome_back,
                    )
                )
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
