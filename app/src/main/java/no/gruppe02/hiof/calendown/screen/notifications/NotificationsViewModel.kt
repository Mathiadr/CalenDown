package no.gruppe02.hiof.calendown.screen.notifications

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import no.gruppe02.hiof.calendown.model.Invitation
import no.gruppe02.hiof.calendown.model.User
import no.gruppe02.hiof.calendown.service.InvitationService
import no.gruppe02.hiof.calendown.service.StorageService
import no.gruppe02.hiof.calendown.service.UserService
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val invitationService: InvitationService,
    private val userService: UserService,
    private val storageService: StorageService
) : ViewModel() {
    val invitations = invitationService.invitations
    val _invitations = mutableStateListOf<InvitationUiState>()

    init {
        viewModelScope.launch {
            invitationService.invitations.first().forEach { invitation ->
                _invitations.add(
                    InvitationUiState(
                        invitation = invitation,
                        senderName = getSender(invitation.senderId).toString(),
                        eventName = getEventName(invitation.eventId).toString()
                        // Fixme
                    )
                )
            }
        }
    }
    fun getRecipient(): String? {
        var recipient: String? = null
        viewModelScope.launch {
            userService.currentUserInfo.first().also { recipient = it.username }
        }
        return recipient
    }

    fun getSender(senderId: String): String? {
        var sender: String? = null
        viewModelScope.launch {
            userService.get(senderId).also { sender = it?.username }
        }
        return sender
    }

    fun getEventName(eventId: String): String? {
        var event: String? = null
        viewModelScope.launch {
            storageService.getEvent(eventId).also { event = it?.title }
        }
        return event
    }

    fun acceptInvitation(){

    }
    fun declineInvitation(){

    }
}