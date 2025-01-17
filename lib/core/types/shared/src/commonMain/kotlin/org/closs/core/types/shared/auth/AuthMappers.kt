package org.closs.core.types.shared.auth

import org.closs.core.types.shared.aliases.DbSession
import org.closs.core.types.shared.auth.dto.AuthDto

fun AuthDto.dtoToDomain(): Session =
    Session(
        accessToken = accessToken,
        refreshToken = refreshToken,
    )

fun Session.sessionToDb(): DbSession =
    DbSession(
        access_token = accessToken,
        refresh_token = refreshToken,
        user_id = user?.id ?: "",
        active = active
    )
