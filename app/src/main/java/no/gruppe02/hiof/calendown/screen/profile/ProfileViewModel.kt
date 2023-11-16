package no.gruppe02.hiof.calendown.screen.profile

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
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

    val userId = authenticationService.currentUserId
    val user = mutableStateOf(User())

    var selectedImgUri by mutableStateOf<Uri?>(null)

    init {
        viewModelScope.launch {  user.value = getUser(userId)!! }
    }

    fun uploadProfileImg (img: Uri) {
        viewModelScope.launch {
            userService.uploadImage(img)
        }
    }

    private suspend fun getUser(userId: String) =
        withContext(Dispatchers.Default) {
            return@withContext userService.get(userId)
        }



}

