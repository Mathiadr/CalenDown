package no.gruppe02.hiof.calendown.screen.notifications

import android.net.Uri
import com.google.firebase.firestore.DocumentId
import no.gruppe02.hiof.calendown.model.Invitation
import no.gruppe02.hiof.calendown.model.User

data class InvitationUiState(
    val uid: String,
    val senderId: String,
    val senderName: String,
    val senderProfileImageUri: Uri?,
    val eventName: String,
    val eventId: String
)