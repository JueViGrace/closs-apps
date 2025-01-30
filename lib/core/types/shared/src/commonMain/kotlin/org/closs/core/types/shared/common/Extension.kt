package org.closs.core.types.shared.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import kotlinx.datetime.LocalDateTime
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.order_status_ready_to_pick
import org.closs.core.resources.resources.generated.resources.order_status_unspecified
import org.jetbrains.compose.resources.StringResource

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

fun String.calculateOrderStatus(): StringResource {
    return when (this) {
        14.toString() -> Res.string.order_status_ready_to_pick
        else -> Res.string.order_status_unspecified
    }
}
