package no.gruppe02.hiof.calendown.model


import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Event(
    @DocumentId val uid: String = "",
    var userId: String = "",

    var title: String = "",
    var description: String = "",
    var icon: String = "",
    var date: Date = Date(),
    )
{
    var timeDifferenceInLong: Long = date.time - System.currentTimeMillis()

    fun update(){
        timeDifferenceInLong = date.time - System.currentTimeMillis()
    }
    fun getSecondsRemaining(): Number{
        return (timeDifferenceInLong / 1000) % 60
    }

    fun getMinutesRemaining(): Number{
        return (timeDifferenceInLong / (1000 * 60)) % 60
    }

    fun getHoursRemaining(): Number{
        return (timeDifferenceInLong / (1000 * 60 * 60)) % 24
    }

    fun getDaysRemaining(): Number{
        return (timeDifferenceInLong / (1000 * 60 * 60 * 24)) % 365
    }

    fun getMonthsRemaining(): Number{
        return (timeDifferenceInLong / (1000 * 60 * 60 * 24)) % 365
    }
}