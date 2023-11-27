package no.gruppe02.hiof.calendown.service.impl

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import no.gruppe02.hiof.calendown.model.Event
import no.gruppe02.hiof.calendown.service.StorageService
import no.gruppe02.hiof.calendown.service.UserService
import javax.inject.Inject

class StorageServiceImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val userService: UserService)
    : StorageService {
    private val TAG = this::class.simpleName

    override val events: Flow<List<Event>> get() =
        firestore.collection(EVENT_COLLECTION).where(
            Filter.or(
                Filter.arrayContains(PARTICIPANTS_FIELD, userService.currentUserId),
                Filter.equalTo(USER_ID_FIELD, userService.currentUserId),
                )).dataObjects()


    override suspend fun deleteEvent(event: Event){
        firestore.collection(EVENT_COLLECTION).document(event.uid).delete()
        //result.reference.delete()
    }

    override suspend fun getEvent(eventId: String): Event? =
        firestore.collection(EVENT_COLLECTION).document(eventId).get().await().toObject()


    override suspend fun save(event: Event): String {
        return firestore.collection(EVENT_COLLECTION).add(event)
            .addOnSuccessListener {
                Log.i(TAG, "Event saved to Firestore")
            }
            .addOnFailureListener {
                Log.e(TAG, "Error occurred while saving event to Firestore", it)
            }
            .await().id
    }



    override suspend fun delete(eventId: String) {
        firestore.collection(EVENT_COLLECTION).document(eventId).delete()
            .addOnSuccessListener {
                Log.i(TAG, "Event deleted from firebase")
            }
            .addOnFailureListener {
                Log.e(TAG, "Error occurred while deleting event:\n$it")
            }

    }
    override suspend fun addParticipant(eventId: String, userId: String) {
        firestore.collection(EVENT_COLLECTION).document(eventId).update(PARTICIPANTS_FIELD, FieldValue.arrayUnion(userId))
            .addOnSuccessListener {
                Log.i(TAG, "Participant added to event")
            }
            .addOnFailureListener {
                Log.e(TAG, "Error occurred while adding participant:\n${it}")
            }
    }
    override suspend fun removeParticipant(eventId: String, userId: String) {
        firestore.collection(EVENT_COLLECTION).document(eventId).update(PARTICIPANTS_FIELD, FieldValue.arrayRemove(userId))
            .addOnSuccessListener {
                Log.i(TAG, "Participant added to event")
            }
            .addOnFailureListener {
                Log.e(TAG, "Error occurred while adding participant:\n${it}")
            }
    }

            companion object {
        private const val EVENT_COLLECTION = "events"
        private const val USER_ID_FIELD = "userId"
        private const val PARTICIPANTS_FIELD = "participants"
    }
}