package org.closs.picking.home.data

import dev.tmapps.konnection.Konnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import org.closs.core.api.shared.KtorClient
import org.closs.core.database.helper.PickingDbHelper
import org.closs.home.shared.data.HomeRepository
import kotlin.coroutines.CoroutineContext

class DefaultHomeRepository(
    private val client: KtorClient,
    private val dbHelper: PickingDbHelper,
    private val konnection: Konnection,
    override val coroutineContext: CoroutineContext,
    override val scope: CoroutineScope,
) : HomeRepository {
    override fun sync() {
    }

    // Todo: should this invalidate server session?
    override suspend fun logOut() {
        scope.async {
            dbHelper.withDatabase { db ->
                db.transaction {
                    db.clossSessionQueries.endSession()
                }
            }
        }.await()
    }
}
