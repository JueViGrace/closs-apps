package org.closs.accloss.home.data

import kotlinx.coroutines.CoroutineScope
import org.closs.shared.home.data.HomeRepository
import kotlin.coroutines.CoroutineContext

class DefaultHomeRepository(
    override val coroutineContext: CoroutineContext,
    override val scope: CoroutineScope
) : HomeRepository {
    override fun sync() {
    }

    override suspend fun logOut() {
    }
}
