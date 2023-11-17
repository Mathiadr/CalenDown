package no.gruppe02.hiof.calendown.service.impl

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import no.gruppe02.hiof.calendown.model.User
import no.gruppe02.hiof.calendown.service.AuthenticationService
import no.gruppe02.hiof.calendown.service.UserService
import javax.inject.Inject


class AuthenticationServiceImpl @Inject constructor(
    private val auth: FirebaseAuth)
    : AuthenticationService {
    private val TAG = this::class.simpleName
    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()
    override val hasUser: Boolean
        get() = auth.currentUser != null

    override suspend fun createAnonymousAccount() {
        auth.signInAnonymously().await()
    }

    override suspend fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it.exception) }
            .addOnFailureListener { onResult(it.cause) }
            .await()
    }


    override suspend fun createAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { onResult(it.exception) }.await()
        Log.i(TAG, "New account registered to Firebase")
    }
    /*
    override suspend fun signOut() {
        auth.signOut()

        auth.signInAnonymously()
    }

     */

    companion object{
        private const val TAG = "AuthenticationServiceImpl"
    }
}

