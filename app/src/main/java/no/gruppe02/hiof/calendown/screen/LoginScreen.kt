package no.gruppe02.hiof.calendown.screen

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
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import no.gruppe02.hiof.calendown.components.ButtonComponent
import no.gruppe02.hiof.calendown.components.ClickableTextComponent
import no.gruppe02.hiof.calendown.components.HeaderText
import no.gruppe02.hiof.calendown.components.InputTextField
import no.gruppe02.hiof.calendown.ui.theme.Gray

@Composable
fun LoginScreen(
    continueAsGuest: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel() ) {

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
                textValue = "Log in"
            )
            Spacer(
                modifier = Modifier
                    .height(120.dp))

            InputTextField(
                labelValue = "Email",
                imageVector = Icons.Outlined.Email)

            InputTextField(
                labelValue = "Password",
                imageVector = Icons.Outlined.Lock)

            Spacer(
                modifier = Modifier
                    .height(80.dp))

            ButtonComponent(
                textValue = "Login"
            )

            Spacer(
                modifier = Modifier
                    .height(50.dp))

            Divider(
                modifier = Modifier
                    .fillMaxWidth(),
                color = Gray,
                thickness = 1.dp
            )
            Spacer(
                modifier = Modifier
                    .height(30.dp))

            ClickableTextComponent(
                initialText = "No account? ",
                actionText = "Create account"
            )


            Spacer(
                modifier = Modifier
                    .height(20.dp)
            )

            ClickableTextComponent(
                initialText = "Continue as ",
                actionText = "guest?",
                onClick = {
                    viewModel.createAnonymousAccount()
                    continueAsGuest()
                }
            )
        }
    }
}
