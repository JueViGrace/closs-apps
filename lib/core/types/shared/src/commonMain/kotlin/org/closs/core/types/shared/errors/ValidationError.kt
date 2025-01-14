package org.closs.core.types.shared.errors

sealed class ValidationError(
    val title: String,
    val description: String
) {
    data class EmptyError(val message: String) : ValidationError(
        title = "Empty",
        description = message,
    )

    data class InvalidInputError(val message: String) : ValidationError(
        title = "Invalid input",
        description = message
    )
}
