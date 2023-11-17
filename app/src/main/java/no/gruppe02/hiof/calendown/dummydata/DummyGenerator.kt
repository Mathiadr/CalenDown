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
import no.gruppe02.hiof.calendown.model.User
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneOffset
import java.util.Date
import java.util.UUID

const val MATHIAS_ID = "OTVjFVYXSVNpvQLyrNTBgD7EGEg2"
const val MATHIAS_FAKE_ID = "dJyRV9KMoIbaelZR4Eb5x5QyJhj1"
const val OLIVER_ID = "kwA1lNGBRZd0zMluHLoM3WC0k763"
const val NIKOLAS_ID = "bmtqUlv0nGdZ4co2m64ud1WaKwG2"


object DummyGenerator{
    val yourBestFriend = User(
        uid = UUID.randomUUID().toString(),
        username = "Best McBro",
        email = "awesomeEmail@brah.com",
        isAnonymous = false
    )
    fun eventList(userId: String, dummyFriend: User): List<Event>{

        return listOf(
            Event(title = "Dentist appointment",
                description = "I hate him so much",
                icon = Icons.Default.Warning.name,
                userId = userId,
                date = addFutureDate(1,5,1)),

            Event(title = "Your best friend's Birthday",
                description = "Bro is old!!!!",
                icon = Icons.Default.Favorite.name,
                userId = dummyFriend.uid,
                date = addFutureDate(2,0,0),
                participants = listOf(
                    userId,
                )),
            Event(title = "Your best friend's concert",
                description = "Bro can sing!!!!",
                icon = Icons.Default.Star.name,
                userId = dummyFriend.uid,
                date = addFutureDate(30,0,0),
                participants = listOf(
                )),
        )
    }
    fun invitations(userId: String, dummyEvent: Event, dummyFriend: User) =
        Invitation(
            recipientId = userId,
            senderId = dummyFriend.uid,
            eventId = dummyEvent.uid
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
