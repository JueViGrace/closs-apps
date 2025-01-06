package org.closs.auth.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.closs.auth.data.repository.AuthRepository
import org.closs.auth.presentation.state.ForgotPasswordState
import org.closs.core.presentation.messages.Messages
import org.closs.core.presentation.navigation.Navigator

class ForgotPasswordViewModel(
    val navigator: Navigator,
    private val messages: Messages,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ForgotPasswordState())
    private val state = _state.asStateFlow()
}
