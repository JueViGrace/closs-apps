package org.closs.core.database.driver

import android.content.Context
import app.cash.sqldelight.async.coroutines.synchronous
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import org.closs.accloss.database.ACCLOSSDB
import org.closs.picking.database.PickingDB

actual class ACCLOSSDriver(
    private val context: Context
) : DriverFactory {
    override suspend fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = ACCLOSSDB.Schema.synchronous(),
            context = context,
            name = "accloss.db"
        )
    }
}

actual class PickingDriver(
    private val context: Context
) : DriverFactory {
    override suspend fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = PickingDB.Schema.synchronous(),
            context = context,
            name = "picking.db"
        )
    }
}