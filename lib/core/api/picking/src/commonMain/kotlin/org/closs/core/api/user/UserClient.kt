package org.closs.core.api.user

import org.closs.core.api.shared.client.ApiOperation
import org.closs.core.api.shared.client.KtorClient
import org.closs.core.api.shared.client.call
import org.closs.core.api.shared.client.get
import org.closs.core.types.picker.dto.PickerDto
import org.closs.core.types.shared.user.dto.UserDto

class UserClient(
    private val client: KtorClient
) {
    suspend fun getUserById(token: String): ApiOperation<UserDto> {
        return client.call {
            get(
                urlString = "/api/users/me",
                headers = mapOf("Authorization" to "Bearer $token")
            )
        }
    }

    suspend fun getUserInfo(token: String): ApiOperation<PickerDto> {
        return client.call {
            get(
                urlString = "/api/users/me/info",
                headers = mapOf("Authorization" to "Bearer $token")
            )
        }
    }
}
