package no.gruppe02.hiof.calendown.model

import com.google.firebase.firestore.DocumentId

class Invitation(
    @DocumentId val uid: String = "",
    val recipientId: String = "",
    val senderId: String = "",
    val status: String = "",
    val eventId: String = "",
)