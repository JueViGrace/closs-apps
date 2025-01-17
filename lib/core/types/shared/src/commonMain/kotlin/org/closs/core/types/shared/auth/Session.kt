package org.closs.core.types.shared.auth

import org.closs.core.types.shared.user.User

data class Session(
    val accessToken: String,
    val refreshToken: String,
    val user: User? = null,
    val active: Boolean = false,
)
