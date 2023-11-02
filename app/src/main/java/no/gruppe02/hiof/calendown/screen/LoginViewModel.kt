package no.gruppe02.hiof.calendown.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import no.gruppe02.hiof.calendown.service.AccountService
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val accountService: AccountService) : ViewModel() {


    fun createAnonymousAccount(){
        viewModelScope.launch {
            if (!accountService.hasUser) accountService.createAnonymousAccount()
        }
    }
}