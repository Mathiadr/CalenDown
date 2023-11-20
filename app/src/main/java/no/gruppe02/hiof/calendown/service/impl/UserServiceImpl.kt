package no.gruppe02.hiof.calendown.service.impl

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import android.net.Uri
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.channels.awaitClose
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import no.gruppe02.hiof.calendown.model.User
import no.gruppe02.hiof.calendown.service.UserService
import java.util.UUID
import javax.inject.Inject

class UserServiceImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore,
    private val storage: FirebaseStorage
) : UserService {
    private val TAG = this::class.simpleName

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()
    override val hasUser: Boolean
        get() = auth.currentUser != null


    override val currentUser: Flow<User> = getUserData(currentUserId)


    // Gathered from ChatGPT
    override fun getUserData(userId: String): Flow<User> =
        callbackFlow {
            val userRef = firestore.collection(USER_COLLECTION).document(userId)

            // Observe changes in the firestore document
            val listeningRegistration = userRef.addSnapshotListener { snapshot, e ->
                if (e != null) {
                    println("Error occured while fetching User data. Setting User data to anonymous")
                    this.trySend(User(uid = userId, isAnonymous = true))
                    close(e)
                    return@addSnapshotListener
                }

                // Parse and emit the user object to the flow
                if (snapshot != null && snapshot.exists()) {
                    Log.i(TAG, "User document found.")
                    val user = snapshot.toObject<User>()
                    if (user != null) {
                        this.trySend(user)
                    } else Log.e(TAG,"Error occurred while parsing the user document to User object")
                }
            }

            // Cancel the listener when the flow is no longer needed
            awaitClose {
                listeningRegistration.remove()
            }
        }
    // TODO: HANDLE ANON USE CASE


    override suspend fun searchUser(nameQuery: String): Flow<List<User>> =
        callbackFlow {
            val docRef = firestore.collection(USER_COLLECTION).document(currentUserId)

            val listenerRegistration = docRef.addSnapshotListener { snapshot, error ->
                if (error != null){
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    try {
                        val currentFriends = snapshot.get(FRIENDS_FIELD) as List<String>
                        launch {
                            val collection = firestore.collection(USER_COLLECTION).where(
                                Filter.greaterThanOrEqualTo(USER_USERNAME_FIELD, nameQuery)
                            )
                            trySend(collection.get().await().toObjects())
                        }
                    }catch (e: Exception){
                        Log.e(TAG, "Exception occurred while searching for queried user")
                    }
                }

            }
            awaitClose {
                listenerRegistration.remove()
            }
        }


    override suspend fun getFriendList(userId: String): Flow<List<User>> =
        callbackFlow {
            val document = firestore.collection(USER_COLLECTION).document(userId)

            val listenerRegistration = document.addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    try {
                        val friends = mutableListOf<User>()
                        val friendIds = snapshot.get(FRIENDS_FIELD) as? List<String> ?: emptyList()
                        friends.clear()
                        Log.i(TAG, "Friends Count: ${friendIds.size}")


                        launch {
                            friendIds.mapNotNull {friendId ->
                                get(friendId)
                            }.let { friends.addAll(it) }
                            trySend(friends.toList())
                        }
                    } catch (e: Error){
                        Log.e(TAG, "Error occurred while fetching friend list:\n", e)
                        trySend(emptyList())
                    }

                }
            }
            awaitClose{
                listenerRegistration.remove()
            }
    }


    override suspend fun getMultipleUsers(userIds: List<String>): Flow<List<User>> =
        callbackFlow {
            val collection = firestore.collection(USER_COLLECTION)
            val users = mutableListOf<User>()
            val listenerRegistration = collection.addSnapshotListener { snapshot, error ->
                launch {
                    userIds.forEach { userId ->
                        try {
                            get(userId)?.let { users.add(it) }
                        }catch (e: Exception){
                            Log.e(TAG, "Error occurred while getting user from list", e)
                        }
                    }
                    trySend(users)
                }

            }
            awaitClose {
                listenerRegistration.remove()
            }
        }
    override suspend fun uploadImage(img: Uri) {
        val documentId = UUID.randomUUID().toString()
        val storageRef = storage.reference
        val imageRef = storageRef.child("images/${documentId} ")
        val uploadTask = imageRef.putFile(img)

        //Register observers to listen for when the download is done or if it fails
        uploadTask.addOnFailureListener {
            // Handle unsuccessful uploads
        }.addOnSuccessListener { taskSnapshot ->
            // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
            // ...
        }
    }

    override suspend fun delete(user: User){
        firestore.collection(USER_COLLECTION).document(user.uid).delete()
    }

    override suspend fun get(userId: String): User? =
        firestore.collection(USER_COLLECTION).document(userId).get().await().toObject()


    override suspend fun save(user: User) {
        firestore.collection(USER_COLLECTION).document(user.uid).set(user).await()
    }

    override suspend fun addFriend(userId: String, friendId: String) {
        // TODO: Make a transaction instead to avoid discrepancy?
        firestore.collection(USER_COLLECTION).document(userId)
            .update(FRIENDS_FIELD, FieldValue.arrayUnion(friendId))
        firestore.collection(USER_COLLECTION).document(friendId)
            .update(FRIENDS_FIELD, FieldValue.arrayUnion(userId))
    }

    override suspend fun removeFriend(userId: String, friendId: String) {
        firestore.collection(USER_COLLECTION).document(userId)
            .update(FRIENDS_FIELD, FieldValue.arrayRemove(friendId))
        firestore.collection(USER_COLLECTION).document(friendId)
            .update(FRIENDS_FIELD, FieldValue.arrayRemove(userId))
    }

    companion object {
        private const val USER_COLLECTION = "users"
        private const val USER_USERNAME_FIELD = "username"
        private const val USER_ID = "id"
        private const val FRIENDS_FIELD = "friends"
    }
}