package no.gruppe02.hiof.calendown.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude

class Invitation(
    @DocumentId val uid: String = "",
    @Exclude var recipientId: String = "",
    var senderId: String = "",
    var eventId: String = "",
)