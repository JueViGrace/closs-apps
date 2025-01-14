package org.closs.user.presentation.state

import org.closs.core.types.shared.user.User

data class UsersListState(
    val users: List<org.closs.core.types.shared.user.User> = emptyList(),
)
