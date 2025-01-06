package org.closs.core.database.driver

import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.closs.core.database.ClossCliDb

actual class DriverFactory(
    private val context: Context
) {
    actual suspend fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = ClossCliDb.Schema.synchronous(),
            context = context,
            name = "closs.db"
        )
    }
}
