package org.closs.core.types.salesman

data class Salesman(
    val code: String,
    val username: String,
    val name: String,
    val email: String,
    val phone: String,
    val phones: String,
    val supervisedBy: String,
    val sector: String,
    val subSector: String,
    val lastSync: String,
    val version: String,
    val createdAt: String,
    val updatedAt: String
)
