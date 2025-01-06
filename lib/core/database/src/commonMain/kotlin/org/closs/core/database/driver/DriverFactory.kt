package org.closs.core.database.driver

import app.cash.sqldelight.db.SqlDriver

expect class DriverFactory {
    suspend fun createDriver(): SqlDriver
}
