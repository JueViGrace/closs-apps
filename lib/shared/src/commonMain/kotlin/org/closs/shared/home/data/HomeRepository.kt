package org.closs.shared.home.data

import org.closs.core.types.shared.data.Repository

interface HomeRepository : Repository {
    fun sync()
    suspend fun logOut()
}
