package org.closs.core.database.driver

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.closs.accloss.database.ACCLOSSDB
import org.closs.picking.database.PickingDB

actual class ACCLOSSDriver : DriverFactory {
    override suspend fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver("jdbc:sqlite:accloss.db")
        ACCLOSSDB.Schema.create(driver)
        return driver
    }
}

actual class PickingDriver : DriverFactory {
    override suspend fun createDriver(): SqlDriver {
        val driver = JdbcSqliteDriver("jdbc:sqlite:picking.db")
        PickingDB.Schema.create(driver)
        return driver
    }
}
