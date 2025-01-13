package org.closs.core.database.shared.driver

import app.cash.sqldelight.db.SqlDriver

interface DriverFactory {
    suspend fun createDriver(): SqlDriver
}


