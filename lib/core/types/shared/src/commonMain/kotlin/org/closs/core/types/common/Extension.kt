package org.closs.core.types.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import kotlinx.datetime.LocalDateTime
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.ic_avlogo
import org.closs.core.resources.resources.generated.resources.ic_wokin_logo
import org.closs.core.types.company.Companies
import org.jetbrains.compose.resources.DrawableResource

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

fun selectCompanyImage(company: String): DrawableResource {
    return when (company) {
        Companies.CLO.code -> Res.drawable.ic_avlogo
        Companies.WOKIN.code -> Res.drawable.ic_wokin_logo
        else -> Res.drawable.ic_avlogo
    }
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
