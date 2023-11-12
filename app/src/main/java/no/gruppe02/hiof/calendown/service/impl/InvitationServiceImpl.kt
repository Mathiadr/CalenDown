package no.gruppe02.hiof.calendown.service.impl

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
import no.gruppe02.hiof.calendown.model.Invitation
import no.gruppe02.hiof.calendown.service.AuthenticationService
import no.gruppe02.hiof.calendown.service.InvitationService
import no.gruppe02.hiof.calendown.service.StorageService
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class InvitationServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AuthenticationService
) : InvitationService {
    override val invitations: Flow<List<Invitation>>
        get() = auth.currentUser.flatMapLatest { user ->
            firestore.collection("${USER_COLLECTION}/${user.uid}/${INVITATION_SUBCOLLECTION}").dataObjects<Invitation>()
                .onEach {
                    it.forEach { invitation -> invitation.recipientId = user.uid }
                }
        }
    override suspend fun delete(invitation: Invitation) {
        println("Deleting invitation ${invitation.uid}... ")
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