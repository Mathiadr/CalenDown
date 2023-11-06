package no.gruppe02.hiof.calendown.screen.createaccount

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Text
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import no.gruppe02.hiof.calendown.R
import no.gruppe02.hiof.calendown.components.ButtonComponent
import no.gruppe02.hiof.calendown.components.HeaderText
import no.gruppe02.hiof.calendown.components.InputTextField


@Composable
fun CreateAccountScreen(
    loggedIn: () -> Unit,
    viewModel: CreateAccountViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderText(
                textValue = "Create account"
            )

            if(uiState.errorMessage != 0)
                Text(text = stringResource(id = uiState.errorMessage),
                    Modifier.padding(vertical = 8.dp))

            Spacer(
                modifier = Modifier
                    .height(120.dp))

            InputTextField(
                uiState.email,
                viewModel::onEmailChange,
                placeholderText = stringResource(R.string.email),
                imageVector = Icons.Outlined.Email)

            Spacer(
                modifier = Modifier
                    .height(10.dp))

            InputTextField(
                uiState.password,
                viewModel::onPasswordChange,
                placeholderText = stringResource(R.string.password),
                imageVector = Icons.Outlined.Lock)

            Spacer(
                modifier = Modifier
                    .height(10.dp))

            InputTextField(
                uiState.repeatPassword,
                viewModel::onRepeatPasswordChange,
                placeholderText = stringResource(R.string.repeat_password),
                imageVector = Icons.Outlined.Lock)

            Spacer(
                modifier = Modifier
                    .height(80.dp))

            ButtonComponent(
                textValue = "Create account",
                onClick = { viewModel.onSignUpClick(loggedIn) }
            )

        }
    }
}
