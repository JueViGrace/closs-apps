package org.closs.core.types.user

data class User(
    val id: String,
    val username: String,
    val code: String,
    val lastSync: String,
    val version: String,
    val createdAt: String,
    val updatedAt: String,
)
