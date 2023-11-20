package no.gruppe02.hiof.calendown.screen.addEvent

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import no.gruppe02.hiof.calendown.model.Event
import no.gruppe02.hiof.calendown.service.AuthenticationService
import no.gruppe02.hiof.calendown.service.StorageService
import java.util.Date
import javax.inject.Inject
@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val storageService: StorageService,
    private val authenticationService: AuthenticationService)
    : ViewModel() {
    fun saveEvent(eventName: String, eventDescription: String, eventDate: Date) {
        viewModelScope.launch {
            storageService.save(Event(userId = authenticationService.currentUserId, title = eventName, description = eventDescription, date = eventDate))
        }
    }
}

