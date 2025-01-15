package org.closs.core.api

import org.closs.core.api.shared.KtorClient

class DefaultKtorClient(
    override val baseUrl: String = "http://192.168.0.1:5000/"
) : KtorClient
