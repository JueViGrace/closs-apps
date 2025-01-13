package org.closs.core.database.helper

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.closs.core.database.driver.DriverFactory
import org.closs.picking.database.PickingDB
import kotlin.coroutines.CoroutineContext

interface PickingDbHelper : DbHelper {
    var db: PickingDB?

    suspend fun <T> withDatabase(block: suspend PickingDbHelper.(PickingDB) -> T): T
}

class PickingDbHelperImpl(
    override val driver: DriverFactory,
    override val coroutineContext: CoroutineContext,
) : PickingDbHelper {
    override var db: PickingDB? = null
    override val mutex: Mutex = Mutex()

    override suspend fun <T> withDatabase(
        block: suspend PickingDbHelper.(PickingDB) -> T
    ): T = mutex.withLock {
        if (db == null) {
            db = createDb()
        }

        return@withLock block(db!!)
    }

    private suspend fun createDb(): PickingDB {
        return PickingDB(driver.createDriver())
    }
}
