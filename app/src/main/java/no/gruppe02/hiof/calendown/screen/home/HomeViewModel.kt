package no.gruppe02.hiof.calendown.screen.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import no.gruppe02.hiof.calendown.data.DummyGenerator
import no.gruppe02.hiof.calendown.model.Event
import no.gruppe02.hiof.calendown.model.EventTimer
import no.gruppe02.hiof.calendown.model.Invitation
import no.gruppe02.hiof.calendown.service.AlarmSchedulerService
import no.gruppe02.hiof.calendown.service.AuthenticationService
import no.gruppe02.hiof.calendown.service.InvitationService
import no.gruppe02.hiof.calendown.service.StorageService
import no.gruppe02.hiof.calendown.service.UserService
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val invitationService: InvitationService,
    private val userService: UserService,
    private val storageService: StorageService,
    private val alarmSchedulerService: AlarmSchedulerService)
    : ViewModel() {
    private val TAG = this::class.simpleName

    val activeEvents = MutableStateFlow<Map<Event, EventTimer>>(emptyMap())

    init {
        viewModelScope.launch {
            try {
                activeEvents.value = storageService.events.first().filter { event: Event ->
                    event.date.time >= System.currentTimeMillis()
                }.sortedBy { event -> event.date.time  }
                    .associateWith { event: Event ->
                    EventTimer(event.date.time)
                }
            } catch(e: Exception) {
                Log.e(TAG, "Error occurred while fetching events", e)
            }
            handleCountdown()
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            activeEvents.value.keys.forEach { event: Event ->
                alarmSchedulerService.cancel(event)
                if (event.userId == authenticationService.currentUserId)
                    storageService.deleteEvent(event)
                else if (event.participants.contains(authenticationService.currentUserId))
                    storageService.removeParticipant(event.uid, authenticationService.currentUserId)
            }
        }
    }

    private fun handleCountdown() {
        viewModelScope.launch {
            while (isActive) {
                activeEvents.value.values.forEach{ eventTimer ->
                    eventTimer.update() }
                delay(1000)
            }
        }
    }

    fun debugPopulateWithDummies(){
        viewModelScope.launch {
            val dummyFriend = DummyGenerator.yourBestFriend
            val userId = userService.currentUserId
            userService.save(dummyFriend)
            userService.addFriend(userId, dummyFriend.uid)
            DummyGenerator.eventList(userId, dummyFriend).forEach {event ->
                val docId = storageService.save(event)
                if (!event.participants.contains(userId) && event.userId != userId){
                    invitationService.create(Invitation(recipientId = userId, senderId = dummyFriend.uid, eventId = docId))
                }
            }
        }
    }
}
