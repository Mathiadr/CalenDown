package no.gruppe02.hiof.calendown.dummydata

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import com.google.type.DateTime
import no.gruppe02.hiof.calendown.model.Event

object Datasource {
    val eventList = listOf(
        Event(title = "Sam's birthday", description = "Happy birthday!", date = DateTime.getDefaultInstance().toString(), icon = Icons.Default.Home, userId = "" ),
        Event(title = "Dentist appointment", description = "I hate him so much", date = DateTime.getDefaultInstance().day.toString(), icon = Icons.Default.Warning, userId = "" ),
        Event(title = "Date with Alex", description = "Time to get lucky tonight", date = DateTime.getDefaultInstance().toString(), icon = Icons.Default.Favorite, userId = "" ),
        Event(title = "Apply for grants", description = "I need the money...", date = DateTime.getDefaultInstance().toString(), icon = Icons.Default.Star, userId = "" ),
    )
}