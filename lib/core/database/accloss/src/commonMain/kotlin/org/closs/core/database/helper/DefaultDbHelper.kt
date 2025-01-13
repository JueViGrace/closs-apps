package org.closs.core.database.helper

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.closs.core.database.ClossDb
import org.closs.core.database.shared.driver.DriverFactory
import org.closs.core.database.shared.helper.DbHelper
import kotlin.coroutines.CoroutineContext

interface ACCLOSSDbHelper : DbHelper {
    var db: ClossDb?

    suspend fun <T> withDatabase(block: suspend ACCLOSSDbHelper.(ClossDb) -> T): T
}

class DefaultDbHelper(
    override val driver: DriverFactory,
    override val coroutineContext: CoroutineContext,
) : ACCLOSSDbHelper {
    override var db: ClossDb? = null
    override val mutex: Mutex = Mutex()

    override suspend fun <T> withDatabase(
        block: suspend ACCLOSSDbHelper.(ClossDb) -> T
    ): T = mutex.withLock {
        if (db == null) {
            db = createDb()
        }

        return@withLock block(db!!)
    }

    private suspend fun createDb(): ClossDb {
        return ClossDb(driver.createDriver())
    }
}
