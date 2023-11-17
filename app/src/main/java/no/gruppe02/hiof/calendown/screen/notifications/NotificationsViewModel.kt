package no.gruppe02.hiof.calendown.screen.notifications

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import no.gruppe02.hiof.calendown.model.Invitation
import no.gruppe02.hiof.calendown.service.AuthenticationService
import no.gruppe02.hiof.calendown.service.InvitationService
import no.gruppe02.hiof.calendown.service.StorageService
import no.gruppe02.hiof.calendown.service.UserService
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val invitationService: InvitationService,
    private val userService: UserService,
    private val storageService: StorageService,
    private val authenticationService: AuthenticationService
) : ViewModel() {
    private val TAG = this::class.simpleName

    val invitations = mutableStateListOf<InvitationUiState>()

    init {
        viewModelScope.launch {
            invitationService.invitations.first().forEach { invitation ->
                invitations.add(
                    InvitationUiState(
                        invitation = invitation,
                        senderName = getSender(invitation.senderId).toString(),
                        eventName = getEventName(invitation.eventId).toString()
                    )
                )
            }
        }
    }

    private suspend fun getSender(senderId: String): String? =
        withContext(Dispatchers.Default) {
            return@withContext userService.get(senderId)?.username
    }

    private suspend fun getEventName(eventId: String): String? =
        withContext(Dispatchers.Default) {
            return@withContext storageService.getEvent(eventId)?.title
    }

    fun acceptInvitation(invitation: Invitation) {
        Log.d(TAG, "User ${invitation.recipientId} accepted invitation ${invitation.uid}")
        viewModelScope.launch {
            storageService.addParticipant(invitation.eventId, invitation.recipientId)
            invitationService.delete(invitation)
        }
    }

    fun declineInvitation(invitation: Invitation) {
        Log.d(TAG, "User declined invitation ${invitation.uid}")
        viewModelScope.launch {
            invitationService.delete(invitation)
        }
    }
}