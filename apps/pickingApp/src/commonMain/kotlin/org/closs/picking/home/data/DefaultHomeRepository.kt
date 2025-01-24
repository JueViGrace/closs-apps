package org.closs.picking.home.data

import dev.tmapps.konnection.Konnection
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.closs.core.database.helper.PickingDbHelper
import org.closs.core.types.auth.dbActiveToDomain
import org.closs.core.types.shared.auth.Session
import org.closs.core.types.shared.state.RequestState
import org.closs.shared.home.data.HomeRepository
import kotlin.coroutines.CoroutineContext

class DefaultHomeRepository(
    private val dbHelper: PickingDbHelper,
    private val konnection: Konnection,
    override val coroutineContext: CoroutineContext,
    override val scope: CoroutineScope,
) : HomeRepository {
    override fun getSession(): Flow<RequestState<Session>> = flow {
        emit(RequestState.Loading)

        dbHelper.withDatabase { db ->
            executeOneAsFlow(
                query = db.sessionQueries.findActiveAccount()
            )
        }.catch { e ->
            emit(
                RequestState.Error(
                    error = e.message ?: ""
                )
            )
        }.collect { session ->
            if (session == null) {
                return@collect emit(
                    RequestState.Error(
                        error = ""
                    )
                )
            }
            emit(
                RequestState.Success(
                    data = session.dbActiveToDomain()
                )
            )
        }
    }.flowOn(coroutineContext)

    override fun sync() {
    }

    // Todo: should this invalidate server session?
    override suspend fun logOut() {
        scope.async {
            dbHelper.withDatabase { db ->
                db.transaction {
                    val session = db.sessionQueries.findActiveAccount()
                        .executeAsOneOrNull()
                        ?: rollback()

                    db.clossSessionQueries.deleteSession(session.user_id)
                    db.clossUserQueries.delete(session.user_id)
                    db.clossPickerQueries.deleteByUser(session.user_id)
                    db.clossConfigQueries.deleteByUser(session.user_id)
                    db.clossProductQueries.deleteByUser(session.user_id)
                }
            }
        }.await()
    }
}
