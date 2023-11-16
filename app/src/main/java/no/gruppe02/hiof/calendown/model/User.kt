package no.gruppe02.hiof.calendown.model

import android.net.Uri
import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId val uid: String = "",
    val username: String = "",
    val email: String = "",
    val profileImg: String = "",
    val isAnonymous: Boolean = true,
    val friends: List<String>? = null,
    val invitations: List<Invitation>? = null,
)
