package org.closs.auth.presentation.events

sealed interface AccountsListEvents {
    data class OnAccountClick(val id: String) : AccountsListEvents
    data object OnSignInNavigate : AccountsListEvents
}
