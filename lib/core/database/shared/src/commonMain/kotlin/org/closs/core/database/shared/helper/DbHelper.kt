package org.closs.core.database.shared.helper

import app.cash.sqldelight.Query
import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOneOrNull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.sync.Mutex
import org.closs.core.database.shared.driver.DriverFactory
import kotlin.coroutines.CoroutineContext

interface DbHelper {
    val driver: DriverFactory
    val coroutineContext: CoroutineContext
    val mutex: Mutex

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

