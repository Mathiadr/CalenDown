package no.gruppe02.hiof.calendown.model

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId val id: String = "",
    val username: String = "",
    val email: String = "",
    val isAnonymous: Boolean = true,
    val invitations: List<Invitation>? = null,
)
