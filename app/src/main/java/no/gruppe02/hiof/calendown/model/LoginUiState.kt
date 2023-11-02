package no.gruppe02.hiof.calendown.model

import androidx.annotation.StringRes

data class LoginUiState (
    val email: String = "",
    val password: String = "",
    val repeatPassword: String = "",
    @StringRes val errorMessage: Int = 0
)