package no.gruppe02.hiof.calendown.screen.notifications

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import no.gruppe02.hiof.calendown.service.InvitationService
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val invitationService: InvitationService
) : ViewModel() {
    //val invitations = invitationService.invitations


}