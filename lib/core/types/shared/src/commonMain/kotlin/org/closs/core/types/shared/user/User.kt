package org.closs.core.types.shared.user

data class User(
    val id: String,
    val username: String,
    val code: String,
    val name: String,
    val lastSync: String,
    val version: String,
    val createdAt: String,
    val updatedAt: String,
)
