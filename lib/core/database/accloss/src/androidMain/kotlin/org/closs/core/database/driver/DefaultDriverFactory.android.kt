package org.closs.core.database.driver

import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.closs.core.database.ClossDb
import org.closs.core.database.shared.driver.DriverFactory

actual class DefaultDriverFactory(
    private val context: Context
) : DriverFactory {
    override suspend fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = ClossDb.Schema.synchronous(),
            context = context,
            name = "accloss.db"
        )
    }
}
