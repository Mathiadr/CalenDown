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
    var participants: List<String> = emptyList()
    )
