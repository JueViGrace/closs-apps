package org.closs.core.api

import org.closs.core.api.shared.KtorClient

class DefaultKtorClient : KtorClient {
    override val baseUrl: String = "http://cloccidental.com/"
}
