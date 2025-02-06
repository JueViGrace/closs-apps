package org.closs.shared.app.presentation.state

import org.jetbrains.compose.resources.StringResource

data class AppState(
    val snackMessage: StringResource? = null,
    val description: String = "",
    val refresh: Boolean = false
)
