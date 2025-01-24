package org.closs.core.api.user

import org.closs.core.api.shared.client.ApiOperation
import org.closs.core.api.shared.client.KtorClient
import org.closs.core.api.shared.client.call
import org.closs.core.api.shared.client.get
import org.closs.core.types.salesman.dto.SalesmanDto
import org.closs.core.types.shared.user.dto.UserDto

class UserClient(
    private val client: KtorClient
) {
    suspend fun getUserById(
        baseUrl: String?,
        token: String,
    ): ApiOperation<UserDto> {
        return client.call {
            get(
                baseUrl = baseUrl,
                urlString = "/api/users/me",
                headers = mapOf("Authorization" to "Bearer $token")
            )
        }
    }

    suspend fun getUserInfo(
        baseUrl: String?,
        token: String
    ): ApiOperation<SalesmanDto> {
        return client.call {
            get(
                baseUrl = baseUrl,
                urlString = "/api/users/me/info",
                headers = mapOf("Authorization" to "Bearer $token")
            )
        }
    }
}
