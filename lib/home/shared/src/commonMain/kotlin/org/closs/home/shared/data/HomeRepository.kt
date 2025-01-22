package org.closs.home.shared.data

import org.closs.core.types.shared.data.Repository

interface HomeRepository : Repository {
    fun sync()
    suspend fun logOut()
}
