package org.closs.core.api.shared.auth

import org.closs.core.api.shared.client.ApiOperation
import org.closs.core.api.shared.client.KtorClient
import org.closs.core.api.shared.client.call
import org.closs.core.api.shared.client.post
import org.closs.core.types.shared.auth.dto.AuthDto
import org.closs.core.types.shared.auth.dto.ForgotPasswordDto
import org.closs.core.types.shared.auth.dto.RefreshTokenDto
import org.closs.core.types.shared.auth.dto.SignInDto

class AuthClient(
    private val client: KtorClient
) {
    suspend fun signIn(
        signInDto: SignInDto,
        baseUrl: String? = null,
    ): ApiOperation<AuthDto> {
        return client.call {
            post(
                baseUrl = baseUrl,
                urlString = "/api/auth/signIn",
                body = signInDto
            )
        }
    }

    suspend fun refresh(
        refreshToken: String,
        baseUrl: String? = null,
    ): ApiOperation<AuthDto> {
        return client.call {
            post(
                baseUrl = baseUrl,
                urlString = "/api/auth/refresh",
                body = RefreshTokenDto(refreshToken),
                headers = mapOf("Authorization" to "Bearer $refreshToken")
            )
        }
    }

    suspend fun forgotPassword(
        forgotPasswordDto: ForgotPasswordDto,
        baseUrl: String? = null,
    ): ApiOperation<AuthDto> {
        return client.call {
            post(
                baseUrl = baseUrl,
                urlString = "/api/auth/forgotPassword",
                body = forgotPasswordDto
            )
        }
    }
}
