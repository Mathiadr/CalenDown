package no.gruppe02.hiof.calendown.service

import kotlinx.coroutines.flow.Flow
import no.gruppe02.hiof.calendown.model.Event
import no.gruppe02.hiof.calendown.model.User

interface UserService {
    val currentUserInfo: Flow<User>
    suspend fun delete(user: User): Unit
    suspend fun get(userId: String): User?
    suspend fun save(user: User): Void
}