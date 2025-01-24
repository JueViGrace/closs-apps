package org.closs.core.api.client

import org.closs.core.api.shared.client.KtorClient

class DefaultKtorClient : KtorClient {
    override val defaultBaseUrl: String = "http://192.168.0.173:5000/"
}
