package org.closs.app.shared.data

import kotlinx.coroutines.flow.Flow
import org.closs.core.types.shared.auth.Session
import org.closs.core.types.shared.data.Repository
import org.closs.core.types.shared.state.RequestState

interface AppRepository : Repository {
    fun validateSession(): Flow<RequestState<Session>>
    fun getAccounts(): Flow<RequestState<List<Session>>>
}
