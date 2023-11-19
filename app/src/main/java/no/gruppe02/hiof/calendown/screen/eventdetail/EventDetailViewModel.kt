package no.gruppe02.hiof.calendown.screen.eventdetail

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import no.gruppe02.hiof.calendown.EVENT_ID
import no.gruppe02.hiof.calendown.model.Event
import no.gruppe02.hiof.calendown.model.EventTimer
import no.gruppe02.hiof.calendown.model.Invitation
import no.gruppe02.hiof.calendown.model.User
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


    // Business logic
    init {
        val eventId = savedStateHandle.get<String>(EVENT_ID)

        if (eventId != null) {
            viewModelScope.launch {
                event.value = storageService.getEvent(eventId) ?: Event()
                eventTimer.value = EventTimer(event.value.date.time)
                owner.value =  userService.get(event.value.userId)!! // TODO: Catch error
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
            storageService.delete(event.value.uid)
        }
    }

    private suspend fun getUser(userId: String) =
        withContext(Dispatchers.Default) {
            return@withContext userService.getUserData(userId)
        }

    fun getFriendList(){
        viewModelScope.launch {
            try {
                userService.getFriendList(userService.currentUserId)
                    // Filter out friends who already have access to the event
                    .map { friends -> friends.filter { !_participants.value.contains(it) } }
                    .collect {
                        friends -> _friendList.value = friends
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
            Log.d(TAG, "Removing user ${participantId} from event")
            storageService.removeParticipant(event.value.uid, participantId)
        }


}