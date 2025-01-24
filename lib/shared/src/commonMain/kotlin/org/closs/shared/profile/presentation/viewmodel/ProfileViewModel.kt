package org.closs.shared.profile.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.shared.profile.data.ProfileRepository
import org.closs.shared.profile.presentation.events.ProfileEvents
import org.closs.shared.profile.presentation.state.ProfileState

abstract class ProfileViewModel(
    protected open val repository: ProfileRepository,
    open val navigator: Navigator,
    protected open val handle: SavedStateHandle
) : ViewModel() {
    open val state: StateFlow<ProfileState> = MutableStateFlow(ProfileState()).asStateFlow()

    abstract fun onEvent(event: ProfileEvents)
}
