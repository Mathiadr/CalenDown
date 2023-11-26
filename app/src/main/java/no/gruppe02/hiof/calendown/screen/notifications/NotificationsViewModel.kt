package no.gruppe02.hiof.calendown.screen.notifications

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import no.gruppe02.hiof.calendown.model.Event
import no.gruppe02.hiof.calendown.model.Invitation
import no.gruppe02.hiof.calendown.model.User
import no.gruppe02.hiof.calendown.service.AlarmSchedulerService
import no.gruppe02.hiof.calendown.service.AuthenticationService
import no.gruppe02.hiof.calendown.service.InvitationService
import no.gruppe02.hiof.calendown.service.StorageService
import no.gruppe02.hiof.calendown.service.UserService
import java.security.cert.CertPath
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val invitationService: InvitationService,
    private val userService: UserService,
    private val storageService: StorageService,
    private val alarmSchedulerService: AlarmSchedulerService
) : ViewModel() {
    private val TAG = this::class.simpleName

    private val _invitations = MutableStateFlow<List<InvitationUiState>>(emptyList())
    val invitations = _invitations.asStateFlow()


    init {
        viewModelScope.launch {
            invitationService.invitations.collect { invitations ->
                _invitations.value = invitations.map {invitation ->
                    val sender = getSender(invitation.senderId)
                    val event = getEvent(invitation.eventId)
                    InvitationUiState(
                        uid = invitation.uid,
                        senderId = invitation.senderId,
                        senderName = sender?.username ?: "Invalid",
                        senderProfileImageUri = getProfileImage(sender?.imgUrl),
                        eventId = invitation.eventId,
                        eventName = event?.title ?: "Invalid",
                        eventDate = SimpleDateFormat.getDateTimeInstance().format(event?.date) ?: "Invalid"
                    )
                }
            }
        }
    }

    private suspend fun getProfileImage(path: String?): Uri? =
        withContext(Dispatchers.Default) {
            if (!path.isNullOrEmpty()) {
                return@withContext userService.getImageUrl(path)
            } else
                return@withContext null
        }

    private suspend fun getSender(senderId: String): User? =
        withContext(Dispatchers.Default) {
            return@withContext userService.get(senderId)
    }

    private suspend fun getSenderName(senderId: String): String =
        withContext(Dispatchers.Default) {
            return@withContext userService.get(senderId)?.username ?: "Invalid"
        }

    private suspend fun getEvent(eventId: String): Event? =
        withContext(Dispatchers.Default) {
            return@withContext storageService.getEvent(eventId)
    }
    fun acceptInvitation(invitationId: String, eventId: String) {
        Log.d(TAG, "User has accepted invitation $invitationId")
        viewModelScope.launch {
            storageService.addParticipant(eventId, userService.currentUserId)
            invitationService.delete(invitationId)
            storageService.getEvent(eventId)?.let { alarmSchedulerService.setAlarm(it) }
        }
    }
    fun declineInvitation(invitationId: String) {
        Log.d(TAG, "User has declined invitation $invitationId")
        viewModelScope.launch {
            invitationService.delete(invitationId)
        }
    }

}