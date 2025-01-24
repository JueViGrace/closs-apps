package org.closs.shared.profile.presentation.state

import org.closs.core.types.shared.user.User

data class ProfileState(
    val user: User? = null,
)
