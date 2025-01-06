package org.closs.core.database.driver

import app.cash.sqldelight.async.coroutines.awaitCreate
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.closs.core.database.ClossCliDb

actual class DriverFactory {
    actual suspend fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver("jdbc:sqlite:closs.db")
        ClossCliDb.Schema.awaitCreate(driver)
        return driver
    }
}
