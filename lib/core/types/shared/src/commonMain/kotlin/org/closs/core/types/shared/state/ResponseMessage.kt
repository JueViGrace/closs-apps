package org.closs.core.types.shared.state

import org.jetbrains.compose.resources.StringResource

data class ResponseMessage(
    val message: StringResource? = null,
    val description: String? = null,
)
