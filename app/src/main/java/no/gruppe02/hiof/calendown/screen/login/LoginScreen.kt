package no.gruppe02.hiof.calendown.screen.login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Divider
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
import no.gruppe02.hiof.calendown.components.ClickableTextComponent
import no.gruppe02.hiof.calendown.components.ElevatedButtonComponent
import no.gruppe02.hiof.calendown.components.EmailField
import no.gruppe02.hiof.calendown.components.HeaderText
import no.gruppe02.hiof.calendown.components.PasswordField

@Composable
fun LoginScreen(
    loggedIn: () -> Unit,
    createAccount: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel() ) {

    val uiState by viewModel.uiState

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeaderText(text = stringResource(R.string.log_in))

            Spacer(modifier = Modifier.height(120.dp))

            if (uiState.errorMessage != 0)
                Text(
                    text = stringResource(id = uiState.errorMessage),
                    color = MaterialTheme.colorScheme.error
                )

            EmailField(
                value = uiState.email,
                onNewValue = viewModel::onEmailChange,
                label = stringResource(R.string.email),
                imageVector = Icons.Outlined.Email
            )

            Spacer(modifier = Modifier.height(10.dp))

            PasswordField(
                value = uiState.password,
                onNewValue = viewModel::onPasswordChange,
                label = stringResource(R.string.password),
                imageVector = Icons.Outlined.Lock
            )

            Spacer(modifier = Modifier.height(80.dp))

            ElevatedButtonComponent(
                label = "Login",
                onClick = { viewModel.onLoginClick(loggedIn) }
            )

            Spacer(modifier = Modifier.height(50.dp))

            Divider(
                modifier = Modifier
                    .fillMaxWidth(),
                thickness = 1.dp
            )

            Spacer(modifier = Modifier.height(30.dp))

            ClickableTextComponent(
                initialText = "No account? ",
                actionText = "Create account",
                onClick = {
                    createAccount()
                }
            )

            Spacer(modifier = Modifier.height(20.dp))

            ClickableTextComponent(
                initialText = "Continue as ",
                actionText = "guest?",
                onClick = {
                    viewModel.createAnonymousAccount(loggedIn)
                }
            )
        }
    }
}