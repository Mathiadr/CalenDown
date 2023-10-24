package no.gruppe02.hiof.calendown.screen.eventdetail

import android.os.CountDownTimer
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import no.gruppe02.hiof.calendown.EVENT_ID
import no.gruppe02.hiof.calendown.api.getTimer
import no.gruppe02.hiof.calendown.model.Event
import no.gruppe02.hiof.calendown.model.Timer
import no.gruppe02.hiof.calendown.service.AccountService
import no.gruppe02.hiof.calendown.service.StorageService
import java.util.Date
import javax.inject.Inject


@HiltViewModel
class EventDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val storageService: StorageService
)
    : ViewModel() {
    // Expose screen UI to state
    val event = mutableStateOf(Event())
    val timer: MutableLiveData<Timer> by lazy {
        MutableLiveData<Timer>()
    }

    // Business logic
    init {
        val eventId = savedStateHandle.get<String>(EVENT_ID)
        if (eventId != null) {
            viewModelScope.launch {
                event.value = storageService.getEvent(eventId) ?: Event()
                timer.value = Timer(event.value.date)
            }
        }
    }
    /*
    // ChatGPT
    fun handleCountdown(targetDate: Date){
        viewModelScope.launch {
            while (isActive){
                val (days, hours, minutes) = getTimer(targetDate)
                _countdownLiveData.postValue(Triple(days, hours, minutes))
                println(_countdownLiveData)
                // Delay for a second
                delay(1000)
            }
        }
    }
    fun stopCountdown(){
        job?.cancel()
    }
    */
}