package org.closs.core.presentation.messages

import org.closs.core.types.state.DataCodes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

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
