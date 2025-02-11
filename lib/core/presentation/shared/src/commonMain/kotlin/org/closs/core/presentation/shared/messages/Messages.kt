package org.closs.core.presentation.shared.messages

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import org.closs.core.types.shared.state.ResponseMessage

// todo: make this global in ui
interface Messages {
    val messages: Flow<ResponseMessage>

    suspend fun sendMessage(code: ResponseMessage)
}

class DefaultMessages : Messages {
    private val _messages = Channel<ResponseMessage>()
    override val messages: Flow<ResponseMessage> = _messages.receiveAsFlow()

    override suspend fun sendMessage(code: ResponseMessage) {
        _messages.send(code)
    }
}
