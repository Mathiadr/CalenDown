package no.gruppe02.hiof.calendown.screen.notifications

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import no.gruppe02.hiof.calendown.model.Invitation
import no.gruppe02.hiof.calendown.ui.theme.CalenDownTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Event Details",
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                )
            )
        },
    ){ innerPadding -> Column(modifier = Modifier.padding(innerPadding)){
        InvitationCard()
    } }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InvitationCard(modifier: Modifier = Modifier){
    val invitation = Invitation(
        uid = "",
        recipientId = "OTVjFVYXSVNpvQLyrNTBgD7EGEg2",
        senderId = "bmtqUlv0nGdZ4co2m64ud1WaKwG2",
        status = "Pending",
        eventId = "HwP1NAJ4ont9e2kVmkDd",
    )
    val recipient = "Mathias"
    val sender = "Oliver"
    val status = "Pending"

    Card (
        onClick = {},
        modifier
            .fillMaxWidth()
            .padding(10.dp),

        ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier
                .padding(10.dp)
                .padding(top = 6.dp, bottom = 6.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Oliver",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(50.dp)
                    .background(Color.Gray, CircleShape))
            Column {
                Text(
                    text = "Oliver",
                    style = MaterialTheme.typography.bodyMedium)
                Text(
                    text = "Event title",
                    style = MaterialTheme.typography.headlineMedium)
                Text(
                    text = "Received 2 days ago",
                    style = MaterialTheme.typography.labelMedium)
            }
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceAround){
                AcceptButton()
                Spacer(modifier = Modifier.width(20.dp))
                DeclineButton()
            }
        }
    }
}

@Composable
fun AcceptButton(){
    IconButton(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .clip(CircleShape)
            .size(60.dp),
        colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color.hsv(110f, 0.5f, 0.9f)),
        ) {
        Icon(modifier = Modifier
            .clip(CircleShape)
            .size(45.dp),
            imageVector = Icons.Default.Check,
            tint = Color.DarkGray,
            contentDescription = "Accept invitation")
    }
}

@Composable
fun DeclineButton(){
    IconButton(
        onClick = { /*TODO*/ },
        modifier = Modifier
            .clip(CircleShape)
            .size(60.dp),
        colors = IconButtonDefaults.iconButtonColors(
            containerColor = Color.hsv(0f, 0.5f, 0.9f)),
    ) {
        Icon(modifier = Modifier
            .clip(CircleShape)
            .size(45.dp),
            imageVector = Icons.Default.Close,
            tint = Color.DarkGray,
            contentDescription = "Decline invitation")
    }
}

@Composable
fun invitation(){

}

@Preview
@Composable
fun InvitationPreview(){
    CalenDownTheme {
        InvitationCard()
    }
}