package no.gruppe02.hiof.calendown.screen.createaccount

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import no.gruppe02.hiof.calendown.R
import no.gruppe02.hiof.calendown.components.BasicScreenLayout
import no.gruppe02.hiof.calendown.components.BasicTextField
import no.gruppe02.hiof.calendown.components.ElevatedButtonComponent
import no.gruppe02.hiof.calendown.components.EmailField
import no.gruppe02.hiof.calendown.components.HeaderText
import no.gruppe02.hiof.calendown.components.PasswordField


@Composable
fun CreateAccountScreen(
    loggedIn: () -> Unit,
    viewModel: CreateAccountViewModel = hiltViewModel()) {

    val uiState by viewModel.uiState

    BasicScreenLayout(innerPadding = PaddingValues(28.dp), modifier = Modifier.verticalScroll(
        rememberScrollState())) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            HeaderText(text = stringResource(R.string.create_account))

            Spacer(modifier = Modifier.height(70.dp))

            if (uiState.errorMessage != 0)
                Text(
                    text = stringResource(id = uiState.errorMessage),
                    color = MaterialTheme.colorScheme.error
                )

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {


                BasicTextField(
                    value = uiState.username,
                    onNewValue = viewModel::onUsernameChange,
                    label = stringResource(R.string.username),
                    imageVector = Icons.Outlined.Person)

                EmailField(
                    uiState.email,
                    viewModel::onEmailChange,
                    label = stringResource(R.string.email),
                    imageVector = Icons.Outlined.Email)

                PasswordField(
                    uiState.password,
                    viewModel::onPasswordChange,
                    label = stringResource(R.string.password),
                    imageVector = Icons.Outlined.Lock)

                PasswordField(
                    uiState.repeatPassword,
                    viewModel::onRepeatPasswordChange,
                    label = stringResource(R.string.repeat_password),
                    imageVector = Icons.Outlined.Lock)
            }

            Spacer(modifier = Modifier.height(70.dp))

            ElevatedButtonComponent(
                label = stringResource(R.string.create_account),
                onClick = { viewModel.onSignUpClick(loggedIn) }
            )
        }
    }
}
