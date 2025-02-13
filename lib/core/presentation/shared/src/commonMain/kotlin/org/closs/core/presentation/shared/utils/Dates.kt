package org.closs.core.presentation.shared.utils

import androidx.compose.runtime.Composable
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import org.closs.core.resources.resources.generated.resources.Res
import org.closs.core.resources.resources.generated.resources.today
import org.closs.core.resources.resources.generated.resources.yesterday
import org.jetbrains.compose.resources.stringResource

@Composable
fun String.toReadableDate(): String {
    val parsedDate: LocalDateTime = LocalDateTime.parse(this)
    val now: LocalDateTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    return when (parsedDate.date) {
        now.date -> {
            stringResource(Res.string.today)
        }
        now.date - DatePeriod(days = 1) -> {
            stringResource(Res.string.yesterday)
        }
        else -> parsedDate.date.toString()
    }
}
