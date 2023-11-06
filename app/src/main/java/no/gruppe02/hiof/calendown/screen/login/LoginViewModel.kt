package no.gruppe02.hiof.calendown.screen.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import no.gruppe02.hiof.calendown.R
import no.gruppe02.hiof.calendown.common.isValidEmail
import no.gruppe02.hiof.calendown.common.isValidPassword
import no.gruppe02.hiof.calendown.model.LoginUiState
import no.gruppe02.hiof.calendown.service.AuthenticationService
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authenticationService: AuthenticationService) : ViewModel() {
    var uiState = mutableStateOf(LoginUiState())
        private set
    private val email
        get() = uiState.value.email

    private val password
        get() = uiState.value.password
    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onLoginClick(loggedIn: () -> Unit) {
        if (!email.isValidEmail()) {
            uiState.value = uiState.value.copy(errorMessage = R.string.email_error)
            return
        }

        else if (!password.isValidPassword()) {
            uiState.value = uiState.value.copy(errorMessage = R.string.password_error)
            return
        }

        viewModelScope.launch {
            try {
                authenticationService.authenticate(email, password) { error ->
                    if (error == null)
                        loggedIn()
                }
            }
            catch(e: Exception) {
                uiState.value = uiState.value.copy(errorMessage = R.string.could_not_log_in)
            }
        }
    }

    fun createAnonymousAccount(loggedIn: () -> Unit){
        viewModelScope.launch {
            if (!authenticationService.hasUser) authenticationService.createAnonymousAccount()
            loggedIn()
        }
    }
}