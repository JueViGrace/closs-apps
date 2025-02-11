package org.closs.shared.home.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.closs.core.types.shared.auth.Session
import org.closs.core.types.shared.data.Repository
import org.closs.core.types.shared.state.RequestState

interface HomeRepository : Repository {
    fun getSession(): Flow<RequestState<Session>>
    fun getOrdersCount(): Flow<Int> = emptyFlow()
    fun sync(): Flow<RequestState<Boolean>>
    suspend fun logOut()
}
