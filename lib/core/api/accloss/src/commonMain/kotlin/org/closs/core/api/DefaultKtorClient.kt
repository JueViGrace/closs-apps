package org.closs.core.api

import org.closs.core.api.shared.KtorClient

class DefaultKtorClient(
    override val baseUrl: String = "http://cloccidental.com/"
) : KtorClient
