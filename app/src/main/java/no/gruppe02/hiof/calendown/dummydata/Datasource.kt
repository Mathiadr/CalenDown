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
import no.gruppe02.hiof.calendown.model.Invitation
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.Date


object Datasource {
    val eventList = listOf(
        Event(title = "Dentist appointment",
            description = "I hate him so much",
            icon = Icons.Default.Warning.name,
            userId = "",
            date = addFutureDate(1,5,1)),
        Event(title = "Mathias' event",
            description = "This event is owned by Mathias",
            icon = Icons.Default.Face.name,
            userId = "OTVjFVYXSVNpvQLyrNTBgD7EGEg2",
            date = addFutureDate(30,0,0),
            participants = listOf(
                "bmtqUlv0nGdZ4co2m64ud1WaKwG2",
                "kwA1lNGBRZd0zMluHLoM3WC0k763"
                )),
        Event(title = "Oliver's event",
            description = "This event is owned by Oliver",
            icon = Icons.Default.Face.name,
            userId = "kwA1lNGBRZd0zMluHLoM3WC0k763",
            date = addFutureDate(0,0,1),
            participants = listOf(
                "OTVjFVYXSVNpvQLyrNTBgD7EGEg2",
                "bmtqUlv0nGdZ4co2m64ud1WaKwG2"
                )),
        Event(title = "Nikolas' event",
            description = "This event is owned by Nikolas",
            icon = Icons.Default.Face.name,
            userId = "bmtqUlv0nGdZ4co2m64ud1WaKwG2",
            date = addFutureDate(1,1,1),
            participants = listOf(
                "OTVjFVYXSVNpvQLyrNTBgD7EGEg2",
                "kwA1lNGBRZd0zMluHLoM3WC0k763"
            )),
    )
    val invitations = listOf(
        Invitation(
            recipientId = "OTVjFVYXSVNpvQLyrNTBgD7EGEg2",
            senderId = "kwA1lNGBRZd0zMluHLoM3WC0k763",
            eventId = "tq3Hx5rXn9aY7bYzaLpF"
        ),
        Invitation(
            recipientId = "OTVjFVYXSVNpvQLyrNTBgD7EGEg2",
            senderId = "bmtqUlv0nGdZ4co2m64ud1WaKwG2",
            eventId = "5PpWmaFDaUzvNaYJ9egP"
        ),
        Invitation(
            recipientId = "bmtqUlv0nGdZ4co2m64ud1WaKwG2",
            senderId = "OTVjFVYXSVNpvQLyrNTBgD7EGEg2",
            eventId = "8128KNIn7wIE5lyF8iI1"
        ),
        Invitation(
            recipientId = "kwA1lNGBRZd0zMluHLoM3WC0k763",
            senderId = "OTVjFVYXSVNpvQLyrNTBgD7EGEg2",
            eventId = "8128KNIn7wIE5lyF8iI1"
        )
    )
    private fun addFutureDate(days: Int, months: Int, years: Int): Date{
        var date = LocalDate.now()
        date = date.plusYears(years.toLong())
        date = date.plusMonths(months.toLong())
        date = date.plusDays(days.toLong())
        return Date.from(date.atTime(LocalTime.now()).toInstant(ZoneOffset.UTC))
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
