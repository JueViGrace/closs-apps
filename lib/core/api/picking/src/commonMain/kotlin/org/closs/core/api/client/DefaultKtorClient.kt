package org.closs.core.api.client

import android.system.Os
import org.closs.core.api.shared.client.KtorClient

class DefaultKtorClient : KtorClient {
    override val defaultBaseUrl: String = Os.getenv("BASE_URL")
}
