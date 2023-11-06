package no.gruppe02.hiof.calendown.service.impl

import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import no.gruppe02.hiof.calendown.model.Invitation
import no.gruppe02.hiof.calendown.service.AuthenticationService
import no.gruppe02.hiof.calendown.service.InvitationService
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class InvitationServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AuthenticationService
) : InvitationService {
    override val invitations: Flow<List<Invitation>>
        get() = auth.currentUser.flatMapLatest { user ->
            firestore.collection("${USER_COLLECTION}/${user.id}/${INVITATION_SUBCOLLECTION}").dataObjects()
        }

    override suspend fun accept(invitation: Invitation) {
        TODO("Not yet implemented")
    }

    override suspend fun decline(invitation: Invitation) {
        TODO("Not yet implemented")
    }

    companion object {
        private const val USER_COLLECTION = "users"
        private const val USER_ID_FIELD = "userId"
        private const val INVITATION_SUBCOLLECTION = "invitations"
        private const val INVITATION_ID_FIELD = "invitationId"
    }
}