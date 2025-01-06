package org.closs.core.database.helper

import app.cash.sqldelight.Query
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import org.closs.core.database.driver.DriverFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.closs.core.database.ClossCliDb
import kotlin.coroutines.CoroutineContext

class DbHelper(
    private val driver: DriverFactory,
    private val coroutineContext: CoroutineContext
) {
    private var db: ClossCliDb? = null
    private val mutex = Mutex()

    suspend fun <Result> withDatabase(block: suspend DbHelper.(ClossCliDb) -> Result): Result = mutex.withLock {
        if (db == null) {
            db = createDb()
        }

        return@withLock block(db!!)
    }

    private suspend fun createDb(): ClossCliDb {
        return ClossCliDb(driver.createDriver())
    }

    fun <T : Any> executeOne(query: Query<T>): T? {
        return query.executeAsOneOrNull()
    }

    fun <T : Any> executeOneAsFlow(query: Query<T>): Flow<T?> {
        return query.asFlow().mapToOneOrNull(coroutineContext)
    }

    fun <T : Any> executeList(query: Query<T>): List<T> {
        return query.executeAsList()
    }

    fun <T : Any> executeListAsFlow(query: Query<T>): Flow<List<T>> {
        return query.asFlow().mapToList(coroutineContext)
    }
}
