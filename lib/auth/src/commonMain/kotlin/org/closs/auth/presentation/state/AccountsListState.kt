package org.closs.auth.presentation.state

import org.closs.core.types.auth.Session

data class AccountsListState(
    val accounts: List<Session> = emptyList(),
    val isLoading: Boolean = false,
)
