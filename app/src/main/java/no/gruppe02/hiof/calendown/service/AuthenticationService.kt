package no.gruppe02.hiof.calendown.service

interface AuthenticationService {

    val currentUserId: String
    val hasUser: Boolean

    suspend fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit)
    suspend fun createAnonymousAccount()
    suspend fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit)
    //suspend fun signOut()
}