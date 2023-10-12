package no.gruppe02.hiof.calendown.service.impl

import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.tasks.await
import no.gruppe02.hiof.calendown.model.Event

import javax.inject.Inject

class StorageServiceImpl
@Inject
constructor(
)  {
    /*
    @OptIn(ExperimentalCoroutinesApi::class)
    override val events: Flow<List<Event>>
        get() = auth.currentUser.flatMapLatest { user ->
            firestore.collection(EVENT_COLLECTION)
                .where(
                    Filter.or(Filter.equalTo(USER_ID_FIELD, user.id),
                        Filter.equalTo(USER_ID_FIELD, "")))
                .dataObjects()
        }

    override suspend fun getEvent(eventId: String): Event? =
        firestore.collection(EVENT_COLLECTION).document(eventId).get().await().toObject()


    override suspend fun save(event: Event): String {
        val movieWithUserId = event.copy(userId = auth.currentUserId)
        return firestore.collection(EVENT_COLLECTION).add(movieWithUserId).await().id
    }



    companion object {
        private const val EVENT_COLLECTION = "events"
        private const val USER_ID_FIELD = "userId"
    }

     */
}