package org.closs.core.database.driver

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.closs.core.database.ClossDb
import org.closs.core.database.shared.driver.DriverFactory
import java.util.Properties

actual class DefaultDriverFactory : DriverFactory {
    override suspend fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver(
            url = "jdbc:sqlite:accloss.db",
            properties = Properties().apply { put("foreign_keys", "true") }
        )
        ClossDb.Schema.create(driver)
        return driver
    }
}
