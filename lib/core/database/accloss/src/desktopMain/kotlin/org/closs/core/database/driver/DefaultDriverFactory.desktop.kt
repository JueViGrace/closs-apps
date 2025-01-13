package org.closs.core.database.driver

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.closs.core.database.shared.driver.DriverFactory

actual class DefaultDriverFactory : DriverFactory {
    override suspend fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver("jdbc:sqlite:accloss.db")
        ClossDb.Schema.create(driver)
        return driver
    }
}