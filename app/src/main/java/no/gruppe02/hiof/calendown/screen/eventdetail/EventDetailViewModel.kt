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
    val event = mutableStateOf(Event())
    val eventTimer = mutableStateOf(EventTimer(0L))
    val owner = mutableStateOf(User())
    val participants = mutableStateListOf<User>()

    private val _friendList = MutableStateFlow<List<User>>(emptyList())
    val friendList = _friendList.asStateFlow()


    // Business logic
    init {
        val eventId = savedStateHandle.get<String>(EVENT_ID)
        if (eventId != null) {
            viewModelScope.launch {
                event.value = storageService.getEvent(eventId) ?: Event()
                eventTimer.value = EventTimer(event.value.date.time)
                owner.value = getUser(event.value.userId)!! // TODO: Catch error
                event.value.participants?.forEach { participant -> participants.add(getUser(participant)!!)
                }
                getFriendList()
                Log.d(TAG, "${_friendList.value.size} Friends acquired")
                _friendList.value.forEach { friend -> Log.d(TAG, "(${friend.uid}): ${friend.username}") }
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
            return@withContext userService.get(userId)
        }

    fun getFriendList(){
        viewModelScope.launch {
            try {
                userService.getFriendList(userService.currentUserId)
                    // Filter out friends who already have access to the event
                    .map { friends -> friends.filter { !participants.contains(it) } }
                    .collect {
                        friends -> _friendList.value = friends
                    }


            } catch (e: Exception){
                Log.e(TAG, "Error occurred while fetching friend list of user", e)
            }
        }
    }

    fun createInvitation(recipientId: String) =
        viewModelScope.launch {
            invitationService.create(Invitation(
                recipientId = recipientId,
                senderId = userService.currentUser.first().uid,
                eventId = event.value.uid
            ))
        }

}