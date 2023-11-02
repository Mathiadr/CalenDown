package no.gruppe02.hiof.calendown.dummydata

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import no.gruppe02.hiof.calendown.model.Event
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date


object Datasource {
    val eventList = listOf(
        Event(title = "Sam's birthday", description = "Happy birthday!", icon = Icons.Default.Home.toString(), userId = "",
            date = addFutureDate(1,0,0)),
        Event(title = "Dentist appointment", description = "I hate him so much", icon = Icons.Default.Warning.toString(), userId = "",
            date = addFutureDate(1,5,1)),
        Event(title = "Date with Alex", description = "Time to get lucky tonight", icon = Icons.Default.Favorite.toString(), userId = "",
            date = addFutureDate(20,2,0)),
        Event(title = "Apply for grants", description = "I need the money...", icon = Icons.Default.Star.toString(), userId = "",
            date = addFutureDate(30,5,2)),
    )

    private fun addFutureDate(days: Int, months: Int, years: Int): Date{
        val defaultZoneId = ZoneId.systemDefault()
        var date = LocalDate.now()
        date = date.plusYears(years.toLong())
        date = date.plusMonths(months.toLong())
        date = date.plusDays(days.toLong())
        return Date.from(date.atStartOfDay(defaultZoneId).toInstant())
    }
}