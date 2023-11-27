package no.gruppe02.hiof.calendown.screen.addEvent

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import no.gruppe02.hiof.calendown.data.EventIcons
import no.gruppe02.hiof.calendown.model.Event
import no.gruppe02.hiof.calendown.service.AlarmSchedulerService
import no.gruppe02.hiof.calendown.service.AuthenticationService
import no.gruppe02.hiof.calendown.service.StorageService
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject
@HiltViewModel
class AddEventViewModel @Inject constructor(
    private val storageService: StorageService,
    private val authenticationService: AuthenticationService,
    private val alarmSchedulerService: AlarmSchedulerService
)
    : ViewModel() {
    private val TAG = this.javaClass.simpleName

    val icons = EventIcons.DefaultIcons.defaultIcons
    fun saveEvent(
        name: String,
        description: String,
        date: Date,
        icon: String?) {
        viewModelScope.launch {
            try {
                val event = Event(
                    userId = authenticationService.currentUserId,
                    title = name,
                    description = description,
                    date = date,
                    icon = icon ?: Icons.Default.DateRange.name
                )
                storageService.save(event).apply {
                    alarmSchedulerService.setAlarm(event)
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error occurred while saving event to database")
            }
        }
    }

    fun selectedDateIsValid(selectedDate: String, selectedTime: String): Boolean{
        if (selectedDate.isBlank() || selectedTime.isBlank()) return false

        return try {
            val date = SimpleDateFormat("dd/MM/yyyy:hh:mm")
                .parse(selectedDate + selectedTime)
            Date().before(date)
        } catch (e: Exception){
            Log.e(TAG, "Error occurred while comparing date with current time. Invalid dates?", e)
            false
        }
    }

    fun getDate(selectedDate: String, selectedTime: String): Date{
        return try {
            SimpleDateFormat("dd/MM/yyyy:hh:mm")
                .parse(selectedDate + selectedTime)!!
        } catch (e: Exception){
            Log.e(TAG, "Error occurred while parsing date. Invalid date?", e)
            throw Exception(e)
        }
    }


}

