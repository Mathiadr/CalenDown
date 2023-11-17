package no.gruppe02.hiof.calendown.service.impl

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.tasks.await
import no.gruppe02.hiof.calendown.model.Invitation
import no.gruppe02.hiof.calendown.service.AuthenticationService
import no.gruppe02.hiof.calendown.service.InvitationService
import no.gruppe02.hiof.calendown.service.StorageService
import no.gruppe02.hiof.calendown.service.UserService
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class InvitationServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val userService: UserService
) : InvitationService {
    private val TAG = this::class.simpleName
    override val invitations: Flow<List<Invitation>>
        get() = userService.currentUser.flatMapLatest { user ->
            firestore.collection("${USER_COLLECTION}/${user.uid}/${INVITATION_SUBCOLLECTION}").dataObjects<Invitation>()
                .onEach {
                    it.forEach { invitation -> invitation.recipientId = user.uid }
                }
        }

    override suspend fun create(invitation: Invitation) {
        firestore.collection("${USER_COLLECTION}/${invitation.recipientId}/${INVITATION_SUBCOLLECTION}").add(invitation).await()
        Log.d(TAG, "Invitation created")
    }
    override suspend fun delete(invitation: Invitation) {
        firestore.collection("${USER_COLLECTION}/${invitation.recipientId}/${INVITATION_SUBCOLLECTION}").document(invitation.uid).delete()
    }

    companion object {
        private const val EVENT_COLLECTION = "events"
        private const val USER_COLLECTION = "users"
        private const val USER_ID_FIELD = "userId"
        private const val INVITATION_SUBCOLLECTION = "invitations"
        private const val INVITATION_ID_FIELD = "invitationId"
    }
}