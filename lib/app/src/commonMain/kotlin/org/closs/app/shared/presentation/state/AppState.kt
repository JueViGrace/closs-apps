package org.closs.app.shared.presentation.state

import org.closs.core.types.shared.auth.Session
import org.jetbrains.compose.resources.StringResource

data class AppState(
    val session: Session? = null,
    val snackMessage: StringResource? = null,
    val description: String = "",
)
