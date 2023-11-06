package no.gruppe02.hiof.calendown.screen.profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import no.gruppe02.hiof.calendown.service.AuthenticationService
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authenticationService: AuthenticationService
) : ViewModel() {

    val userId = authenticationService.currentUserId
}