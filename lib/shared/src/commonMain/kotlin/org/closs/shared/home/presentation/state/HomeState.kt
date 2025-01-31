package org.closs.shared.home.presentation.state

import org.closs.core.types.shared.auth.Session

data class HomeState(
    val session: Session? = null,
    val orderCount: Int = 0,

    val isSyncing: Boolean = false,
    val showDialog: Boolean = false,
)
