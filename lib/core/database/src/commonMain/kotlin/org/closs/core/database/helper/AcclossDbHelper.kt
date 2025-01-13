package org.closs.core.database.helper

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.closs.accloss.database.ACCLOSSDB
import org.closs.core.database.driver.DriverFactory
import kotlin.coroutines.CoroutineContext

interface ACCLOSSDbHelper : DbHelper {
    var db: ACCLOSSDB?

    suspend fun <T> withDatabase(block: suspend ACCLOSSDbHelper.(ACCLOSSDB) -> T): T
}

class ACCLOSSDbHelperImpl(
    override val driver: DriverFactory,
    override val coroutineContext: CoroutineContext,
) : ACCLOSSDbHelper {
    override var db: ACCLOSSDB? = null
    override val mutex: Mutex = Mutex()

    override suspend fun <T> withDatabase(
        block: suspend ACCLOSSDbHelper.(ACCLOSSDB) -> T
    ): T = mutex.withLock {
        if (db == null) {
            db = createDb()
        }

        return@withLock block(db!!)
    }

    private suspend fun createDb(): ACCLOSSDB {
        return ACCLOSSDB(driver.createDriver())
    }
}
