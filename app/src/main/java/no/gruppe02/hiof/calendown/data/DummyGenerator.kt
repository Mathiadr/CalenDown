package no.gruppe02.hiof.calendown.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
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


// Meant for debugging or demonstration purposes
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

