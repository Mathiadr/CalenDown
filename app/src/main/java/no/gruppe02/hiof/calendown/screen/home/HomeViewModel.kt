package no.gruppe02.hiof.calendown.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import no.gruppe02.hiof.calendown.dummydata.Datasource
import no.gruppe02.hiof.calendown.model.Event
import no.gruppe02.hiof.calendown.model.EventTimer
import no.gruppe02.hiof.calendown.service.AuthenticationService
import no.gruppe02.hiof.calendown.service.StorageService
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val storageService: StorageService)
    : ViewModel() {

    val activeEvents = MutableStateFlow<Map<Event, EventTimer>>(emptyMap())

    init {
        viewModelScope.launch {
            try {
                activeEvents.value = storageService.events.first().filter { event: Event ->
                    event.date.time >= System.currentTimeMillis()
                }.associateWith { event: Event ->
                    EventTimer(event.date.time)
                }
            } catch(e: Exception) {
                error("Error occurred while fetching events")
            } finally {
                if (activeEvents.first().isEmpty()) {
                    Datasource.eventList.forEach { event ->
                        if(event.userId.isEmpty()) {
                            event.userId = authenticationService.currentUserId
                        }
                        storageService.save(event)
                    }
            }
        }
            handleCountdown()
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            activeEvents.value.keys.forEach { event: Event -> storageService.deleteEvent(event) }
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
}
