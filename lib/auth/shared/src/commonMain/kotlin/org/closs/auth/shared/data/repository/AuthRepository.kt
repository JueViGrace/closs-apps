package org.closs.auth.shared.data.repository

import kotlinx.coroutines.flow.Flow
import org.closs.core.types.shared.auth.Session
import org.closs.core.types.shared.auth.dto.ForgotPasswordDto
import org.closs.core.types.shared.auth.dto.SignInDto
import org.closs.core.types.shared.data.Repository
import org.closs.core.types.shared.state.DataCodes
import org.closs.core.types.shared.state.RequestState

interface AuthRepository : Repository {
    suspend fun signIn(signInDto: SignInDto): RequestState<DataCodes>
    suspend fun forgotPassword(forgotPasswordDto: ForgotPasswordDto): RequestState<DataCodes>
    fun getAccounts(): Flow<RequestState<List<Session>>>
    suspend fun startSession(id: String)
}

