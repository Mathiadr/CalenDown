package no.gruppe02.hiof.calendown.model


import com.google.firebase.firestore.DocumentId
import no.gruppe02.hiof.calendown.api.getTimer
import java.util.Date

data class Event(
    @DocumentId val uid: String = "",
    var userId: String = "",

    var title: String = "",
    var description: String = "",
    var icon: String = "",
    var date: Date = Date(),
    var countdown: Triple<Long, Long, Long> = getTimer(date)
    )
