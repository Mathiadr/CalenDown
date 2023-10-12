package no.gruppe02.hiof.calendown.model

import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Event(
    @DocumentId val uid: String = "",
    var userId: String = "",
    
    var title: String = "",
    var description: String = "",
    var image: String = "",
    var icon: String = "",
    var category: String = "",

    var date: Date = Date(),
    )
