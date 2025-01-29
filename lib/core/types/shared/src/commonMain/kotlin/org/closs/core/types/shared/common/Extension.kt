package org.closs.core.types.shared.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import kotlinx.datetime.LocalDateTime

@Composable
fun String.capitalizeString(): String {
    return this
        .lowercase()
        .split(" ")
        .joinToString(
            separator = " ",
            transform = { it.capitalize(Locale.current) }
        )
}

fun Throwable.log(tag: String) =
    println(
        """
            $tag, 
            Message: ${this.message}\n
            Localized Message: ${this.localizedMessage}
        """.trimIndent()
    )

fun LocalDateTime.formatDate(): String {
    return "${this.date} ${this.time}"
}
