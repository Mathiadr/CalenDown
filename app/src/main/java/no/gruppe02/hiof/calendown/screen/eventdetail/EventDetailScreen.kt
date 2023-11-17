@file:OptIn(ExperimentalMaterial3Api::class)

package no.gruppe02.hiof.calendown.screen.eventdetail

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import no.gruppe02.hiof.calendown.dummydata.DefaultIcons
import no.gruppe02.hiof.calendown.model.EventTimer
import no.gruppe02.hiof.calendown.model.User
import no.gruppe02.hiof.calendown.ui.theme.CalenDownTheme
import java.text.SimpleDateFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(modifier: Modifier = Modifier,
                      viewModel: EventDetailViewModel = hiltViewModel()
) {
    val event = viewModel.event.value
    val eventTimer = viewModel.eventTimer.value
    val dateString = SimpleDateFormat.getDateTimeInstance().format(event.date)
    val owner = viewModel.owner.value
    val participants = viewModel.participants.toList()
    val addFriendsToEventList = viewModel.friendList.collectAsStateWithLifecycle()

    viewModel.getFriendList()
    Log.d("Screen", "Count: ${addFriendsToEventList.value.size}")
    addFriendsToEventList.value.forEach { user ->
        Log.d("Screen", "\tName: ${user.username}")

    }

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
            Box(modifier = Modifier){
                val show = remember {
                    mutableStateOf(false)
                }

                IconButton(onClick = { show.value = !show.value}) {
                    Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Dropdown Menu")
                }
                DropdownMenu(expanded = show.value, onDismissRequest = { show.value = false }) {
                    DropdownMenuItem(
                        text = { Text(text = "Delete Event") },
                        onClick = { viewModel.deleteEvent() },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete this event"
                            )
                        } )
                    DropdownMenuItem(
                        text = { Text(text = "Add friends to event") },
                        onClick = { /* TODO */ },
                        trailingIcon = {
                            Icon(
                                imageVector = Icons.Default.AddCircle,
                                contentDescription = "Delete this event"
                            )
                        })
                    }


                }
            },

    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(25.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Icon(
                imageVector = DefaultIcons.defaultIcons.getOrDefault(
                    event.icon,
                    Icons.Default.DateRange
                ),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            GenericInformation(
                title = event.title, 
                owner = owner,
                description = event.description, 
                date = dateString
            )
            LazyColumn(content = {
                addFriendsToEventList.value.forEach { friend ->
                    item {
                        ListItem (headlineText = {
                            Text(text = friend.username)
                        },
                            leadingContent = {
                                Icon(imageVector = Icons.Default.Face,
                                    contentDescription = null,
                                    modifier = Modifier.size(25.dp))
                            })
                        Divider()
                    }
                    viewModel.getFriendList()
                }
            })
            if (event.participants.isNotEmpty()){
                Participants(participants = participants)
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)) {
                Timer(eventTimer = eventTimer)
            }
        }
    }
}

@Composable
fun GenericInformation(title: String, date: String, owner: User, description: String){
    Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(text = title, style = MaterialTheme.typography.headlineLarge)
        Text(text = date, style = MaterialTheme.typography.headlineSmall)
        Text(text = "by ${owner.username}", style = MaterialTheme.typography.headlineSmall)
        Text(text = description)
    }
}

@Composable
fun Participants(participants: List<User>){
    Column {
        Text(text = "Participants of this event:")

        LazyColumn(content = {
            participants.forEach { user ->
                item {
                    Participant(participant = user)
                }
            }
        })

    }

}


@Composable
fun Participant(participant: User){
    ListItem (headlineText = {
        Text(text = participant.username)
    },
        leadingContent = {
            Icon(imageVector = Icons.Default.Face,
                contentDescription = null,
                modifier = Modifier.size(25.dp))
        })
    Divider()
}

@Composable
fun EventDropdownMenu(){

}

@Composable
fun Timer(eventTimer: EventTimer){
    val years = eventTimer.years.value
    val months = eventTimer.months.value
    val days = eventTimer.days.value
    val hours = eventTimer.hours.value
    val minutes = eventTimer.minutes.value
    val seconds = eventTimer.seconds.value

    Column(verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(
            text = years,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(end = 5.dp),
            textAlign = TextAlign.End)
        Text(
            text = "years",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(end = 2.dp),
            textAlign = TextAlign.End)
    }
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(
            text = months,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(end = 5.dp),
            textAlign = TextAlign.End)
        Text(
            text = "months",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(end = 2.dp),
            textAlign = TextAlign.End)
    }
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(
            text = days,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(end = 5.dp),
            textAlign = TextAlign.End)
        Text(
            text = "days",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(end = 2.dp),
            textAlign = TextAlign.End)
    }
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(
            text = hours,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(end = 5.dp),
            textAlign = TextAlign.End)
        Text(
            text = "hours",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(end = 2.dp),
            textAlign = TextAlign.End)
    }
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(
            text = minutes,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(end = 5.dp),
            textAlign = TextAlign.End)
        Text(
            text = "minutes",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(end = 2.dp),
            textAlign = TextAlign.End)
    }
    Column(verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(
            text = seconds,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier
                .padding(end = 5.dp),
            textAlign = TextAlign.End)
        Text(
            text = "seconds",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(end = 2.dp),
            textAlign = TextAlign.End)
    }
}

@Composable
@Preview
fun EventDetailScreenPreview(){
    CalenDownTheme {
        EventDetailScreen()
    }
}