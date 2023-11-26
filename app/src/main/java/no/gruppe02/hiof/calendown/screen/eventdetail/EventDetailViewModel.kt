package no.gruppe02.hiof.calendown.screen.eventdetail

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import no.gruppe02.hiof.calendown.EVENT_ID
import no.gruppe02.hiof.calendown.model.Event
import no.gruppe02.hiof.calendown.model.EventTimer
import no.gruppe02.hiof.calendown.model.Invitation
import no.gruppe02.hiof.calendown.model.User
import no.gruppe02.hiof.calendown.service.AlarmSchedulerService
import no.gruppe02.hiof.calendown.service.InvitationService
import no.gruppe02.hiof.calendown.service.StorageService
import no.gruppe02.hiof.calendown.service.UserService
import javax.inject.Inject


@HiltViewModel
class EventDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val storageService: StorageService,
    private val userService: UserService,
    private val invitationService: InvitationService,
    private val alarmSchedulerService: AlarmSchedulerService
)
    : ViewModel() {
    private val TAG = this::class.simpleName

    // Expose screen UI to state
    val currentUserId = userService.currentUserId

    val event = mutableStateOf(Event())
    val eventTimer = mutableStateOf(EventTimer(0L))
    val owner = mutableStateOf(User())

    private val _participants = MutableStateFlow<List<User>>(emptyList())
    val participants = _participants.asStateFlow()

    private val _friendList = MutableStateFlow<List<User>>(emptyList())
    val friendList = _friendList.asStateFlow()

    private val _userImages = mutableStateMapOf<String, Uri?>()
    val userImages = _userImages



    // Business logic
    init {
        val eventId = savedStateHandle.get<String>(EVENT_ID)

        if (eventId != null) {
            viewModelScope.launch {
                event.value = storageService.getEvent(eventId) ?: Event()
                eventTimer.value = EventTimer(event.value.date.time)
                owner.value =  userService.getUserData(event.value.userId).first() // TODO: Catch error
                getFriendList()
                getParticipants()
                handleCountdown()
            }
        }
    }

    private fun handleCountdown(){
        viewModelScope.launch {
            while (isActive){
                eventTimer.value.update()
                // Delay for a second
                delay(1000)
            }
        }
    }

     fun deleteEvent(){
        viewModelScope.launch {
            alarmSchedulerService.cancel(event.value)
            storageService.delete(event.value.uid)
        }
    }

    private suspend fun getUser(userId: String) =
        withContext(Dispatchers.Default) {
            return@withContext userService.getUserData(userId)
        }

    private suspend fun getProfileImage(path: String): Uri? =
        withContext(Dispatchers.Default) {
            if (path.isNotEmpty()) {
                return@withContext userService.getImageUrl(path)
            } else
                return@withContext null
        }

    fun getFriendList(){
        viewModelScope.launch {
            try {
                userService.getFriendList(userService.currentUserId)
                    // Filter out friends who already have access to the event
                    .map { friends -> friends.filter { !_participants.value.contains(it) } }
                    .collect { friends ->
                        _friendList.value = friends
                        friends.forEach { friend ->
                            if (!_userImages.containsKey(friend.uid)) {
                                _userImages[friend.uid] = getProfileImage(friend.imgUrl)
                            }
                        }
                    }


            } catch (e: Exception){
                Log.e(TAG, "Error occurred while fetching friend list of user", e)
            }
        }
    }
    fun getParticipants() =
        viewModelScope.launch {
            try {
                userService.getMultipleUsers(event.value.participants).collect { participants ->
                    _participants.value = participants
                    participants.forEach { participant ->
                        if (!_userImages.containsKey(participant.uid)) {
                            _userImages[participant.uid] = getProfileImage(participant.imgUrl)
                        }
                    }
                }
            } catch (e: Exception){
                Log.e(TAG, "Error occurred while fetching participants", e)
            }
        }

    fun createInvitation(recipientId: String) =
        viewModelScope.launch {
            Log.d(TAG, "Creating invitation for $recipientId")
            invitationService.create(Invitation(
                recipientId = recipientId,
                senderId = userService.currentUser.first().uid,
                eventId = event.value.uid
            ))
        }

    fun removeFromEvent(participantId: String) =
        viewModelScope.launch {
            storageService.removeParticipant(event.value.uid, participantId)
        }


}