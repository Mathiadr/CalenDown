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
        Event(title = "Sam's birthday", description = "Happy birthday!", icon = Icons.Default.Home.toString(), userId = "" ),
        Event(title = "Dentist appointment", description = "I hate him so much", icon = Icons.Default.Warning.toString(), userId = "" ),
        Event(title = "Date with Alex", description = "Time to get lucky tonight", icon = Icons.Default.Favorite.toString(), userId = "" ),
        Event(title = "Apply for grants", description = "I need the money...", icon = Icons.Default.Star.toString(), userId = "" ),
    )
}