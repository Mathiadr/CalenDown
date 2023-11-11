package no.gruppe02.hiof.calendown.model

import com.google.firebase.firestore.DocumentId

class Invitation(
    @DocumentId val uid: String = "",
    var recipientId: String = "",
    var senderId: String = "",
    var eventId: String = "",
)