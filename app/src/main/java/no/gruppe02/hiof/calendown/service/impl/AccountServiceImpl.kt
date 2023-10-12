package no.gruppe02.hiof.calendown.service.impl


/*
class AccountServiceImpl @Inject constructor(private val auth: FirebaseAuth) : AccountService {

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override val hasUser: Boolean
        get() = auth.currentUser != null

    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener =
                FirebaseAuth.AuthStateListener { auth ->
                    this.trySend(auth.currentUser?.let { User(it.uid) } ?: User())
                }
            auth.addAuthStateListener(listener)
            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override suspend fun authenticate(email: String, password: String, onResult: (Throwable?) -> Unit) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { onResult(it.exception) }.await()
    }
    override suspend fun createAnonymousAccount() {
        auth.signInAnonymously().await()
    }

    override suspend fun linkAccount(email: String, password: String, onResult: (Throwable?) -> Unit) {
        // Av en eller annen grunn har fungerer ikke linkingen av kontoer nå?
        // val credential = EmailAuthProvider.getCredential(email, password)
        // auth.currentUser!!.linkWithCredential(credential).addOnCompleteListener { onResult(it.exception) }.await()
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { onResult(it.exception) }.await()
    }

    override suspend fun signOut() {
        auth.signOut()

        auth.signInAnonymously()
    }
}

 */