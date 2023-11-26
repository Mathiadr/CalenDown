package no.gruppe02.hiof.calendown.screen.notifications

import android.net.Uri

data class InvitationUiState(
    val uid: String,
    val senderId: String,
    val senderName: String,
    val senderProfileImageUri: Uri?,
    val eventName: String,
    val eventId: String,
    val eventDate: String
)