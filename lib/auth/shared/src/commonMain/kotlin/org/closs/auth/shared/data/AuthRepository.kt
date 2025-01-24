package org.closs.auth.shared.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import org.closs.core.types.shared.auth.Session
import org.closs.core.types.shared.auth.dto.ForgotPasswordDto
import org.closs.core.types.shared.auth.dto.SignInDto
import org.closs.core.types.shared.data.Repository
import org.closs.core.types.shared.state.DataCodes
import org.closs.core.types.shared.state.RequestState

interface AuthRepository : Repository {
    suspend fun signIn(baseUrl: String? = null, signInDto: SignInDto): Flow<RequestState<DataCodes>>
    suspend fun forgotPassword(baseUrl: String? = null, forgotPasswordDto: ForgotPasswordDto): RequestState<DataCodes>
    fun getAccounts(): Flow<RequestState<List<Session>>>{
        return emptyFlow()
    }
    suspend fun startSession(id: String) = Unit
}

