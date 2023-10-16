package no.gruppe02.hiof.calendown.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import java.util.Date

data class Event(
    @DocumentId val uid: String = "",
    var userId: String = "",

    var title: String = "",
    var description: String = "",
    var icon: String = "",
    var date: Date? = null
    )
