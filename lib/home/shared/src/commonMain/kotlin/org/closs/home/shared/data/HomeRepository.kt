package org.closs.home.shared.data

interface HomeRepository {
    fun sync()
    suspend fun logOut()
}
