package org.closs.core.types.auth

import org.closs.core.types.user.User

data class Session(
    val accessToken: String,
    val refreshToken: String,
    val user: User,
    val active: Boolean = false,
)
