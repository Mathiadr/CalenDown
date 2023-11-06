package no.gruppe02.hiof.calendown.screen.createaccount

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import no.gruppe02.hiof.calendown.R
import no.gruppe02.hiof.calendown.common.isValidEmail
import no.gruppe02.hiof.calendown.common.isValidPassword
import no.gruppe02.hiof.calendown.model.LoginUiState
import no.gruppe02.hiof.calendown.model.User
import no.gruppe02.hiof.calendown.service.AuthenticationService
import no.gruppe02.hiof.calendown.service.UserService
import javax.inject.Inject

@HiltViewModel
class CreateAccountViewModel @Inject constructor(
    private val authenticationService: AuthenticationService,
    private val userService: UserService
) : ViewModel() {

    var uiState = mutableStateOf(LoginUiState())
        private set

    private val username
        get() = uiState.value.username

    private val email
        get() = uiState.value.email

    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }
    fun onUsernameChange(newValue: String) {
        uiState.value = uiState.value.copy(username = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun onSignUpClick(loggedIn: () -> Unit) {
        if (!email.isValidEmail()) {
            uiState.value = uiState.value.copy(errorMessage = R.string.email_error)
            return
        }

        else if (!password.isValidPassword()) {
            uiState.value = uiState.value.copy(errorMessage = R.string.password_error)
            return
        }

        else if (password != uiState.value.repeatPassword) {
            uiState.value = uiState.value.copy(errorMessage = R.string.password_match_error)
            return
        }

        viewModelScope.launch {
            try {
                authenticationService.linkAccount(email, password) { error ->
                    if (error == null)
                        loggedIn()
                }
                userService.save(User(
                    id = authenticationService.currentUserId,
                    username = username,
                    email = email,
                    friends = emptyList(),
                    invitations = emptyList()
                ))
            }
            catch(e: Exception) {
                uiState.value = uiState.value.copy(errorMessage = R.string.could_not_create_account)
            }
        }
    }
}