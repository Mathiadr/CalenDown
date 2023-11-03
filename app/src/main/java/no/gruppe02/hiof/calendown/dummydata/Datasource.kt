package no.gruppe02.hiof.calendown.dummydata

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.ui.graphics.vector.ImageVector
import no.gruppe02.hiof.calendown.model.Event
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date


object Datasource {
    val eventList = listOf(
        Event(title = "Sam's birthday", description = "Happy birthday!", icon = Icons.Default.Home.name, userId = "",
            date = addFutureDate(1,0,0)),
        Event(title = "Dentist appointment", description = "I hate him so much", icon = Icons.Default.Warning.name, userId = "",
            date = addFutureDate(1,5,1)),
        Event(title = "Date with Alex", description = "Time to get lucky tonight", icon = Icons.Default.Favorite.name, userId = "",
            date = addFutureDate(20,2,0)),
        Event(title = "Apply for grants", description = "I need the money...", icon = Icons.Default.Star.name, userId = "",
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

object DefaultIcons {
    val defaultIcons: Map<String, ImageVector> = mapOf(
        Pair(Icons.Default.Home.name, Icons.Default.Home),
        Pair(Icons.Default.Warning.name, Icons.Default.Warning),
        Pair(Icons.Default.Star.name, Icons.Default.Star),
        Pair(Icons.Default.Favorite.name, Icons.Default.Favorite),
        Pair(Icons.Default.Info.name, Icons.Default.Info),
        Pair(Icons.Default.Edit.name, Icons.Default.Edit),
        Pair(Icons.Default.Face.name, Icons.Default.Face),
        Pair(Icons.Default.FavoriteBorder.name, Icons.Default.FavoriteBorder),
        Pair(Icons.Default.Build.name, Icons.Default.Build),
        Pair(Icons.Default.DateRange.name, Icons.Default.DateRange),
        Pair(Icons.Default.Call.name, Icons.Default.Call),
        Pair(Icons.Default.Create.name, Icons.Default.Create),
        Pair(Icons.Default.Email.name, Icons.Default.Email),
        Pair(Icons.Default.LocationOn.name, Icons.Default.LocationOn),
        Pair(Icons.Default.ShoppingCart.name, Icons.Default.ShoppingCart),
        Pair(Icons.Default.ThumbUp.name, Icons.Default.ThumbUp)
    )
}
