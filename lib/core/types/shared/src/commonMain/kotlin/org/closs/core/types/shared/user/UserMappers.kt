package org.closs.core.types.shared.user

import org.closs.core.types.shared.aliases.DbUser
import org.closs.core.types.shared.user.dto.UserDto

fun UserDto.dtoToDomain(): User =
    User(
        id = id,
        username = username,
        code = code,
        name = name,
        lastSync = lastSync,
        version = version,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )

fun User.domainToDb(): DbUser = DbUser(
    id = id,
    username = username,
    code = code,
    last_sync = lastSync,
    version = version,
    created_at = createdAt,
    updated_at = updatedAt
)
