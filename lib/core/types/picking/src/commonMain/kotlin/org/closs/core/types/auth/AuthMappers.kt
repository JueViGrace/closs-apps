package org.closs.core.types.auth

import org.closs.core.database.FindActiveAccount
import org.closs.core.types.shared.auth.Session
import org.closs.core.types.shared.user.User

fun FindActiveAccount.dbActiveToDomain(): Session =
    Session(
        accessToken = access_token,
        refreshToken = refresh_token,
        user = User(
            id = id ?: "",
            username = username ?: "",
            code = code ?: "",
            name = name ?: "",
            lastSync = last_sync ?: "",
            version = version ?: "",
            createdAt = created_at ?: "",
            updatedAt = updated_at ?: "",
        )
    )
