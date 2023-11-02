package no.gruppe02.hiof.calendown.service.impl

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.dataObjects
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import no.gruppe02.hiof.calendown.model.Event
import no.gruppe02.hiof.calendown.service.AccountService
import no.gruppe02.hiof.calendown.service.StorageService
import javax.inject.Inject

class StorageServiceImpl
@Inject
constructor(
    private val firestore: FirebaseFirestore,
    private val auth: AccountService
) : StorageService {

    @OptIn(ExperimentalCoroutinesApi::class)
    override val events: Flow<List<Event>> get() = firestore.collection(EVENT_COLLECTION).dataObjects()


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
*/

    override suspend fun getEvent(eventId: String): Event? =
        firestore.collection(EVENT_COLLECTION).document(eventId).get().await().toObject()


    override suspend fun save(event: Event): String {
        return firestore.collection(EVENT_COLLECTION).add(event).await().id
    }

    override suspend fun delete(eventId: String) {
        firestore.collection(EVENT_COLLECTION).document(eventId).delete()
    }

    companion object {
        private const val EVENT_COLLECTION = "events"
    }
}