package org.closs.home.shared.presentation.state

import org.closs.core.types.shared.auth.Session

data class HomeState(
    val session: Session? = null,

    val isSyncing: Boolean = false,
    val showDialog: Boolean = false,
)
