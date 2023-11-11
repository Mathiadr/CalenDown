package no.gruppe02.hiof.calendown.screen.notifications

import com.google.firebase.firestore.DocumentId
import no.gruppe02.hiof.calendown.model.Invitation

data class InvitationUiState(
    val invitation: Invitation,
    val senderName: String,
    val eventName: String
)