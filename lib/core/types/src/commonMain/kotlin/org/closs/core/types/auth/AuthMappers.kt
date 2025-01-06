package org.closs.core.types.auth

import org.closs.core.database.Closs_session
import org.closs.core.database.FindAccounts
import org.closs.core.database.FindActiveAccount
import org.closs.core.shared.types.auth.AuthDto
import org.closs.core.types.user.User
import org.closs.core.types.user.dtoToDomain

private typealias DbSession = Closs_session

fun FindActiveAccount.dbActiveToDomain(): Session = Session(
    accessToken = access_token,
    refreshToken = refresh_token,
    user = User(
        id = id ?: "",
        username = username ?: "",
        code = code ?: "",
        lastSync = last_sync ?: "",
        version = version ?: "",
        createdAt = created_at ?: "",
        updatedAt = updated_at ?: "",
    )
)

fun FindAccounts.dbAccountsToDomain(): Session = Session(
    accessToken = access_token,
    refreshToken = refresh_token,
    user = User(
        id = id ?: "",
        username = username ?: "",
        code = code ?: "",
        lastSync = last_sync ?: "",
        version = version ?: "",
        createdAt = created_at ?: "",
        updatedAt = updated_at ?: "",
    )
)

fun AuthDto.dtoToDomain(): Session = Session(
    accessToken = accessToken,
    refreshToken = refreshToken,
    user = user.dtoToDomain()
)

fun Session.sessionToDb(): DbSession = DbSession(
    access_token = accessToken,
    refresh_token = refreshToken,
    user_id = user.id,
    active = active
)
