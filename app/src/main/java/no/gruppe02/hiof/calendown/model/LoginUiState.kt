package no.gruppe02.hiof.calendown.model

import androidx.annotation.StringRes

data class LoginUiState (
    val username: String = "",
    val email: String = "",
    val username: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    @StringRes val errorMessage: Int = 0
)