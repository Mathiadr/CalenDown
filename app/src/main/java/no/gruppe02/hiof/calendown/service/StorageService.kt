package no.gruppe02.hiof.calendown.service

import kotlinx.coroutines.flow.Flow
import no.gruppe02.hiof.calendown.model.Event
import no.gruppe02.hiof.calendown.model.Invitation


interface StorageService {
    val events: Flow<List<Event>>
    suspend fun deleteEvent(event: Event): Unit
    suspend fun getEvent(eventId: String): Event?
    suspend fun save(event: Event): String
    suspend fun delete (eventId: String)

    suspend fun addParticipant(eventId: String, userId: String)

}