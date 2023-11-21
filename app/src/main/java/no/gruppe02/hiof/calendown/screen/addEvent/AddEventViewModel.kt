package no.gruppe02.hiof.calendown.screen.addEvent

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import no.gruppe02.hiof.calendown.datasource.EventIcons
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

    val icons = EventIcons.DefaultIcons.defaultIcons
    fun saveEvent(
        name: String,
        description: String,
        date: Date,
        icon: String?) {
        viewModelScope.launch {
            storageService.save(
                Event(
                    userId = authenticationService.currentUserId,
                    title = name,
                    description = description,
                    date = date,
                    icon = icon ?: Icons.Default.DateRange.name
                    ))
        }
    }
}

