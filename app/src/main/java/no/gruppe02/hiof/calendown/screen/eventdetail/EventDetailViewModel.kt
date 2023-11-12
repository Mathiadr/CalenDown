package no.gruppe02.hiof.calendown.screen.eventdetail

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import no.gruppe02.hiof.calendown.EVENT_ID
import no.gruppe02.hiof.calendown.api.getDays
import no.gruppe02.hiof.calendown.api.getHours
import no.gruppe02.hiof.calendown.api.getMinutes
import no.gruppe02.hiof.calendown.api.getMonths
import no.gruppe02.hiof.calendown.api.getRemainingTime
import no.gruppe02.hiof.calendown.api.getSeconds
import no.gruppe02.hiof.calendown.api.getYears
import no.gruppe02.hiof.calendown.model.Event
import no.gruppe02.hiof.calendown.model.EventTimer
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
    private val invitationService: InvitationService
)
    : ViewModel() {
    // Expose screen UI to state
    val event = mutableStateOf(Event())
    val eventTimer = mutableStateOf(EventTimer(0L))
    val owner = mutableStateOf(User())
    val participants = mutableStateListOf<User>()

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


                handleCountdown()
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


    private fun handleCountdown(){
        viewModelScope.launch {
            while (isActive){
                eventTimer.value.update()
                // Delay for a second
                delay(1000)
            }
        }
    }
}