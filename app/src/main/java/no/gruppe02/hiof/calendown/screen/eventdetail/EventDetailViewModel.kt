package no.gruppe02.hiof.calendown.screen.eventdetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import no.gruppe02.hiof.calendown.EVENT_ID
import no.gruppe02.hiof.calendown.model.Event
import no.gruppe02.hiof.calendown.service.AccountService
import no.gruppe02.hiof.calendown.service.StorageService
import javax.inject.Inject


@HiltViewModel
class EventDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val storageService: StorageService
)
    : ViewModel() {

    val event = mutableStateOf(Event())
    init {
        val eventId = savedStateHandle.get<String>(EVENT_ID)
        if (eventId != null) {
            viewModelScope.launch {
                event.value = storageService.getEvent(eventId) ?: Event()
            }
        }
    }
}