package no.gruppe02.hiof.calendown.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import no.gruppe02.hiof.calendown.dummydata.Datasource
import no.gruppe02.hiof.calendown.model.Event
import no.gruppe02.hiof.calendown.service.StorageService
import javax.inject.Inject

// TODO: implement accountService
@HiltViewModel
class HomeViewModel @Inject constructor(private val storageService: StorageService)
    : ViewModel() {

    val events = storageService.events

    init {
        createAnonymousAccount()

        viewModelScope.launch {
            if (events.first().isEmpty()) {
                Datasource.eventList.forEach { event -> storageService.save(event) }
            }
        }
    }

    fun createEvent(eventTitle: String) {
        viewModelScope.launch { storageService.save(Event(title = eventTitle)) }
    }


    private fun createAnonymousAccount(){
        /*
        viewModelScope.launch {
            if (!accountService.hasUser) accountService.createAnonymousAccount()
        }
         */
    }
}