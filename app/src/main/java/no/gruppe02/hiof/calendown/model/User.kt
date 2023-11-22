package no.gruppe02.hiof.calendown.model

import com.google.firebase.firestore.DocumentId

data class User(
    @DocumentId val uid: String = "",
    val username: String = "",
    val email: String = "",
    val imgUrl: String = "",
    val isAnonymous: Boolean = true,
    val friends: List<String> = emptyList(),
)
