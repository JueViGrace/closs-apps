package org.closs.auth.shared.presentation.state

import org.closs.core.types.shared.auth.Session

data class AccountsListState(
    val accounts: List<Session> = emptyList(),
    val isLoading: Boolean = false,
)
