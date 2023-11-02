package no.gruppe02.hiof.calendown.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import no.gruppe02.hiof.calendown.dummydata.Datasource
import no.gruppe02.hiof.calendown.model.Event
import no.gruppe02.hiof.calendown.service.AccountService
import no.gruppe02.hiof.calendown.service.StorageService
import javax.inject.Inject

// TODO: implement accountService
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val accountService: AccountService,
    private val storageService: StorageService)
    : ViewModel() {

    private val _events = MutableStateFlow(emptyList<Event>())
    //val events: StateFlow<List<Event>> = _events
    val events = storageService.events

    init {
        viewModelScope.launch {
            if (events.first().isEmpty()) {
                Datasource.eventList.forEach { event ->
                    event.userId = accountService.currentUserId
                    storageService.save(event) }
            }
        }
    }

    fun createEvent(eventTitle: String) {
        viewModelScope.launch { storageService.save(Event(title = eventTitle)) }
    }
}