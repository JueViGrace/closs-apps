package org.closs.core.types.user

import org.closs.core.shared.types.user.UserDto
import org.closs.core.types.aliases.DbUser

fun UserDto.dtoToDomain(): User = User(
    id = id,
    username = username,
    code = code,
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
