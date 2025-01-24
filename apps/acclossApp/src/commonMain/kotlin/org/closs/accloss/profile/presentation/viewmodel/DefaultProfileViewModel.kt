package org.closs.accloss.profile.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.closs.core.presentation.shared.navigation.Navigator
import org.closs.shared.profile.data.ProfileRepository
import org.closs.shared.profile.presentation.events.ProfileEvents
import org.closs.shared.profile.presentation.state.ProfileState
import org.closs.shared.profile.presentation.viewmodel.ProfileViewModel

class DefaultProfileViewModel(
    override val repository: ProfileRepository,
    override val navigator: Navigator,
    override val handle: SavedStateHandle,
) : ProfileViewModel(
    repository = repository,
    navigator = navigator,
    handle = handle
) {
    private val _state: MutableStateFlow<ProfileState> = MutableStateFlow(ProfileState())
    override val state: StateFlow<ProfileState> = _state.asStateFlow()

    override fun onEvent(event: ProfileEvents) {
    }
}
