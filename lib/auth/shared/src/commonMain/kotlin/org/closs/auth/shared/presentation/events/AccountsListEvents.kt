package org.closs.auth.shared.presentation.events

sealed interface AccountsListEvents {
    data class OnAccountClick(val id: String) : AccountsListEvents
    data object OnSignInNavigate : AccountsListEvents
}
