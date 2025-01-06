package org.closs.user.presentation.viewmodel

import androidx.lifecycle.ViewModel
import org.closs.user.presentation.state.UserDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class UserDetailsViewModel : ViewModel() {
    private val _state = MutableStateFlow(UserDetailsState())
    val state = _state.asStateFlow()
}
