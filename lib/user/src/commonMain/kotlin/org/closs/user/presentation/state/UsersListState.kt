package org.closs.user.presentation.state

import org.closs.core.types.user.User

data class UsersListState(
    val users: List<User> = emptyList(),
)
