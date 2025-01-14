package org.closs.core.presentation.shared.messages

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import org.closs.core.types.shared.state.DataCodes

interface Messages {
    val messages: Flow<DataCodes>

    suspend fun sendMessage(code: DataCodes)
}

class DefaultMessages : Messages {
    private val _messages = Channel<DataCodes>()
    override val messages: Flow<DataCodes> = _messages.receiveAsFlow()

    override suspend fun sendMessage(code: DataCodes) {
        _messages.send(code)
    }
}
