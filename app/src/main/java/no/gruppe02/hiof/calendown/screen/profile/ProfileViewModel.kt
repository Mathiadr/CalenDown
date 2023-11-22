package no.gruppe02.hiof.calendown.screen.profile

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import no.gruppe02.hiof.calendown.model.User
import no.gruppe02.hiof.calendown.service.AuthenticationService
import no.gruppe02.hiof.calendown.service.UserService
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    authenticationService: AuthenticationService,
    private val userService: UserService
) : ViewModel() {
    private val TAG = this::class.simpleName

    val userId = authenticationService.currentUserId
    val currentUser = mutableStateOf<User>(User())

    private val _searchResults = MutableStateFlow<List<User>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    private val _searchResultProfileImages = mutableStateMapOf<String, Uri?>()
    val searchResultProfileImages = _searchResultProfileImages

    private val _friendList = MutableStateFlow<List<User>>(emptyList())
    val friendList = _friendList.asStateFlow()

    private val _friendImages = mutableStateMapOf<String, Uri?>()
    val friendImages = _friendImages

    private val _userImage = mutableStateOf<Uri?>(null)
    val userImage: State<Uri?> = _userImage

    init {
        viewModelScope.launch {
            currentUser.value = userService.currentUser.first()
            _userImage.value = getProfileImage(currentUser.value.imgUrl)
        }
    }

    private suspend fun getProfileImage(path: String): Uri? =
        withContext(Dispatchers.Default) {
            if (path.isNotEmpty()) {
                return@withContext userService.getImageUrl(path)
            } else
                return@withContext null
        }

    fun uploadProfileImg (img: Uri) {
        viewModelScope.launch {
            userService.uploadProfileImg(img, userId)
        }
    }

    fun searchUsers(query: String){
        viewModelScope.launch {
            if (query.length < 2) {
                userService.searchUser(query).collect {queryResult ->
                    _searchResults.value = queryResult
                    queryResult.map {user ->
                        searchResultProfileImages.put(user.uid, getProfileImage(user.imgUrl))
                    }
                }
            }
        }
    }

    private suspend fun getUser(userId: String) =
        withContext(Dispatchers.Default) {
            return@withContext userService.get(userId)
        }

    fun sendFriendRequest(recipientId: String){
        viewModelScope.launch {
            userService.addFriend(currentUser.value.uid, recipientId)
        }
    }

    fun removeFriend(friendId: String){
        viewModelScope.launch {
            userService.removeFriend(currentUser.value.uid, friendId)
        }
    }

    fun getFriendList() {
        viewModelScope.launch {
            try {
                userService.getFriendList(userService.currentUserId)
                    .collect { friends ->
                        _friendList.value = friends

                        friends.forEach{ friend ->
                            _friendImages[friend.uid] = getProfileImage(friend.imgUrl)
                        }
                    }
            } catch (e: Exception){
                Log.e(TAG, "Error occurred while fetching friend list of user", e)
            }
        }
    }
}

