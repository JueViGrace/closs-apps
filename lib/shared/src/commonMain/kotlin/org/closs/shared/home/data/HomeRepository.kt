package org.closs.shared.home.data

import kotlinx.coroutines.flow.Flow
import org.closs.core.types.shared.auth.Session
import org.closs.core.types.shared.data.Repository
import org.closs.core.types.shared.state.RequestState

interface HomeRepository : Repository {
    fun getSession(): Flow<RequestState<Session>>
    fun sync()
    suspend fun logOut()
}
