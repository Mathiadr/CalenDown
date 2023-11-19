package no.gruppe02.hiof.calendown.service

import android.net.Uri
import kotlinx.coroutines.flow.Flow
import no.gruppe02.hiof.calendown.model.User

interface UserService {
    val currentUserId: String
    val hasUser: Boolean
    val currentUser: Flow<User>

    fun getUserData(userId: String): Flow<User>

    suspend fun getFriendList(userId: String): Flow<List<User>>
    suspend fun searchUser(nameQuery: String): Flow<List<User>>
    suspend fun getMultipleUsers(userIds: List<String>): Flow<List<User>>
    suspend fun delete(user: User)
    suspend fun get(userId: String): User?
    suspend fun save(user: User)
    suspend fun addFriend(userId: String, friendId: String)
    suspend fun removeFriend(userId: String, friendId: String)
    suspend fun uploadImage(img: Uri)

}