package org.closs.auth.shared.data.repository

import kotlinx.coroutines.flow.Flow
import org.closs.core.shared.types.auth.ForgotPasswordDto
import org.closs.core.shared.types.auth.SignInDto
import org.closs.core.types.auth.Session
import org.closs.core.types.data.Repository
import org.closs.core.types.state.DataCodes
import org.closs.core.types.state.RequestState

interface AuthRepository : Repository {
    suspend fun signIn(signInDto: SignInDto): RequestState<DataCodes>
    suspend fun forgotPassword(forgotPasswordDto: ForgotPasswordDto): RequestState<DataCodes>
    fun getAccounts(): Flow<RequestState<List<Session>>>
    suspend fun startSession(id: String)
}

