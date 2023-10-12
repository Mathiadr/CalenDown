package no.gruppe02.hiof.calendown.service

import kotlinx.coroutines.flow.Flow
import no.gruppe02.hiof.calendown.model.Event


interface StorageService {
    val events: Flow<List<Event>>
    suspend fun getEvent(eventId: String): Event?
    suspend fun save(event: Event): String
}