package no.gruppe02.hiof.calendown.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Divider
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.gruppe02.hiof.calendown.components.ButtonComponent
import no.gruppe02.hiof.calendown.components.ClickableTextComponent
import no.gruppe02.hiof.calendown.components.HeaderText
import no.gruppe02.hiof.calendown.components.InputTextField
import no.gruppe02.hiof.calendown.ui.theme.Gray

@Composable
fun LoginScreen(continueAsGuest: () -> Unit) {
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
                    .height(20.dp))

            InputTextField(
                labelValue = "Email",
                imageVector = Icons.Outlined.Email)

            InputTextField(
                labelValue = "Password",
                imageVector = Icons.Outlined.Lock)

            Spacer(
                modifier = Modifier
                    .height(230.dp))

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
                    .height(20.dp))

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
                onClick = continueAsGuest
            )
        }
    }
}
