package org.closs.core.database.driver

import app.cash.sqldelight.db.SqlDriver

interface DriverFactory {
    suspend fun createDriver(): SqlDriver
}

expect class ACCLOSSDriver : DriverFactory

expect class PickingDriver : DriverFactory
