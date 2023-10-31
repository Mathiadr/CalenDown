package no.gruppe02.hiof.calendown.screen.eventdetail

import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import no.gruppe02.hiof.calendown.EVENT_ID
import no.gruppe02.hiof.calendown.api.getDays
import no.gruppe02.hiof.calendown.api.getHours
import no.gruppe02.hiof.calendown.api.getMinutes
import no.gruppe02.hiof.calendown.api.getMonths
import no.gruppe02.hiof.calendown.api.getRemainingTime
import no.gruppe02.hiof.calendown.api.getSeconds
import no.gruppe02.hiof.calendown.api.getYears
import no.gruppe02.hiof.calendown.model.Event
import no.gruppe02.hiof.calendown.service.StorageService
import javax.inject.Inject


@HiltViewModel
class EventDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val storageService: StorageService
)
    : ViewModel() {
    // Expose screen UI to state
    val event = mutableStateOf(Event())
    val remainingTimeLong = mutableLongStateOf(0L)

        // Business logic
    init {
        val eventId = savedStateHandle.get<String>(EVENT_ID)
        if (eventId != null) {
            viewModelScope.launch {
                event.value = storageService.getEvent(eventId) ?: Event()
                remainingTimeLong.longValue = getRemainingTime(event.value.date.time)
                handleCountdown()
            }
        }
    }

    fun handleCountdown(){
        viewModelScope.launch {
            while (isActive){
                remainingTimeLong.longValue = getRemainingTime(event.value.date.time)
                // Delay for a second
                delay(1000)
            }
        }
    }

    fun getRemainingSeconds(): String {
        return getSeconds(remainingTimeLong.longValue).toString()
    }
    fun getRemainingMinutes(): String{
        return getMinutes(remainingTimeLong.longValue).toString()
    }
    fun getRemainingHours(): String{
        return getHours(remainingTimeLong.longValue).toString()
    }
    fun getRemainingDays(): String{
        return getDays(remainingTimeLong.longValue).toString()
    }

    fun getRemainingMonths(): String{
        return getMonths(remainingTimeLong.longValue).toString()
    }

    fun getRemainingYears(): String{
        return getYears(remainingTimeLong.longValue).toString()
    }
}