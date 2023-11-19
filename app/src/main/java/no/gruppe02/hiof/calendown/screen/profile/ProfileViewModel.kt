package no.gruppe02.hiof.calendown.screen.profile

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
    var selectedImgUri by mutableStateOf<Uri?>(null)

    private val _searchResults = MutableStateFlow<List<User>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    private val _friendList = MutableStateFlow<List<User>>(emptyList())
    val friendList = _friendList.asStateFlow()

    init {
        viewModelScope.launch {
            currentUser.value = userService.currentUser.first()
        }
    }

    fun uploadProfileImg (img: Uri) {
        viewModelScope.launch {
            userService.uploadImage(img)
        }
    }

    fun searchUsers(query: String){
        viewModelScope.launch {
            if (query.length < 2) {
                userService.searchUser(query).collect {queryResult ->
                    _searchResults.value = queryResult
                }
            }
        }
    }

    private suspend fun getUser(userId: String) =
        withContext(Dispatchers.Default) {
            return@withContext userService.get(userId)
        }

    fun getFriendList() {
        viewModelScope.launch {
            try {
                userService.getFriendList(userService.currentUserId)
                    .collect {
                            friends -> _friendList.value = friends
                    }

            } catch (e: Exception){
                Log.e(TAG, "Error occurred while fetching friend list of user", e)
            }
        }
    }
}

