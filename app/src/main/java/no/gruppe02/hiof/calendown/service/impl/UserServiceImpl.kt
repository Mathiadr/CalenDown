package no.gruppe02.hiof.calendown.service.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import no.gruppe02.hiof.calendown.model.User
import no.gruppe02.hiof.calendown.service.UserService
import javax.inject.Inject

class UserServiceImpl @Inject constructor(
    private val auth: AuthenticationServiceImpl,
    private val firestore: FirebaseFirestore
) : UserService {
    @OptIn(ExperimentalCoroutinesApi::class)
    override val currentUserInfo: Flow<User>
        get() = auth.currentUser.flatMapLatest { user ->
            firestore.collection(USER_COLLECTION).document(user.uid).get().await().toObject()!!
        }
    // TODO: HANDLE ANON USE CASE


    override suspend fun delete(user: User){
        firestore.collection(USER_COLLECTION).document(user.uid).delete()
    }

    override suspend fun get(userId: String): User? =
        firestore.collection(USER_COLLECTION).document(userId).get().await().toObject()


    override suspend fun save(user: User): Void {
        return firestore.collection(USER_COLLECTION).document(user.uid).set(user).await()
    }

    companion object {
        private const val USER_COLLECTION = "users"
        private const val USER_ID_FIELD = "id"
        private const val INVITATION_FIELD = "invitations"
    }
}