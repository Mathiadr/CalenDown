package no.gruppe02.hiof.calendown.service

import kotlinx.coroutines.flow.Flow
import no.gruppe02.hiof.calendown.model.User

interface AccountService {

    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<User>

    suspend fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit)
    suspend fun createAnonymousAccount()
    suspend fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit)
    //suspend fun signOut()


}