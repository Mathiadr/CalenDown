@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package no.gruppe02.hiof.calendown.screen.eventdetail

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import no.gruppe02.hiof.calendown.components.HeaderText
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
    val participants = viewModel.participants.collectAsStateWithLifecycle().value
    viewModel.getParticipants()

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost= {
            SnackbarHost(hostState = snackbarHostState)
        },
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
                ),
                actions = {
                    EventDropdownMenu(viewModel, snackbarHostState)
                }
            )

        }

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
            GenericInformation(viewModel)
            if (participants.isNotEmpty()){
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
fun GenericInformation(viewModel: EventDetailViewModel){
    val event = viewModel.event.value
    val dateString = SimpleDateFormat.getDateTimeInstance().format(event.date)
    val owner = viewModel.owner.value
    Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(text = event.title, style = MaterialTheme.typography.headlineLarge)
        Text(text = dateString, style = MaterialTheme.typography.headlineSmall)
        Text(text = "by ${owner.username}", style = MaterialTheme.typography.headlineSmall)
        Text(text = event.description)
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


@OptIn(ExperimentalMaterial3Api::class)
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

// Dropdown menu for actions against the current event
@Composable
fun EventDropdownMenu(viewModel: EventDetailViewModel,
                      snackbarHostState: SnackbarHostState){
    var expanded by remember { mutableStateOf(false)}
    var openInviteDialog by remember { mutableStateOf(false)}
    var openDeleteEventDialog by remember { mutableStateOf(false)}
    var openRemoveParticipantDialog by remember { mutableStateOf(false)}
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    fun launchSnackbar(message: String){
        scope.launch{
            snackbarHostState.showSnackbar("Test: $message")
        }
    }

    Box(modifier = Modifier
        .wrapContentSize()) {
        IconButton(onClick = { expanded = !expanded}) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Actions for the event")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = { Text(text = "Invite friends to event") },
                onClick = {openInviteDialog = true})
            if (viewModel.owner.value.uid == viewModel.currentUserId){
                DropdownMenuItem(
                    text = { Text(text = "Edit event (Not yet implemented)") },
                    onClick = {/* TODO */ })
                DropdownMenuItem(
                    text = { Text(text = "Manage participants") },
                    onClick = {openRemoveParticipantDialog = true})
                DropdownMenuItem(
                    text = { Text(text = "Delete Event") },
                    onClick = { openDeleteEventDialog = true },
                    colors = MenuDefaults.itemColors(textColor = Color.Red))
            } else {
                DropdownMenuItem(
                    text = { Text(text = "Leave event") },
                    onClick = {
                        viewModel.removeFromEvent(viewModel.currentUserId)
                        navController.previousBackStackEntry
                    })
            }
        }
    }
    if (openInviteDialog)
        InviteToEventDialog(
            viewModel = viewModel,
            closeDialog = {
                openInviteDialog = false
                expanded = false
            })
    if (openDeleteEventDialog)
        DeleteEventDialog(
            viewModel = viewModel,
            closeDialog = {
                openDeleteEventDialog = false
                expanded = false
            })
    if (openRemoveParticipantDialog)
        RemoveParticipantFromEventDialog(
            viewModel = viewModel,
            closeDialog = {
                openRemoveParticipantDialog = false
                expanded = false
            })
}


// https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary#AlertDialog(kotlin.Function0,kotlin.Function0,androidx.compose.ui.Modifier,kotlin.Function0,kotlin.Function0,kotlin.Function0,kotlin.Function0,androidx.compose.ui.graphics.Shape,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,androidx.compose.ui.unit.Dp,androidx.compose.ui.window.DialogProperties)
@Composable
fun InviteToEventDialog(
    viewModel: EventDetailViewModel,
    closeDialog: () -> Unit
    ){
    val friends = viewModel.friendList.collectAsStateWithLifecycle()
    val selectedFriends = remember { mutableStateListOf<User>() }
    viewModel.getFriendList()

    Dialog(
        onDismissRequest = { closeDialog() }){
        Surface(modifier = Modifier
            .width(300.dp)
            .height(400.dp)
            .padding(16.dp),
            shape = RoundedCornerShape(16.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                HeaderText(text = "Invite")
                Divider()
                LazyColumn(
                    content = {
                    friends.value.forEach { friend ->
                        item {
                            ListItem (
                                headlineText = {
                                    Text(text = friend.username)
                                },
                                leadingContent = {
                                    Icon(imageVector = Icons.Default.Face,
                                        contentDescription = null,
                                        modifier = Modifier.size(25.dp))
                                },
                                trailingContent = {
                                    if (friend.uid != viewModel.currentUserId){
                                        Checkbox(checked = selectedFriends.contains(friend), onCheckedChange = {
                                            if (!selectedFriends.contains(friend)) selectedFriends.add(friend)
                                            else selectedFriends.remove(friend)
                                        })
                                    }

                                })
                        }
                        viewModel.getFriendList()
                    }
                })
                Divider()
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                    TextButton(
                        onClick = {
                            Log.d("EventDetailScreen", "Sending invites to ${selectedFriends.size}")
                            selectedFriends.forEach { user -> viewModel.createInvitation(user.uid) }
                            closeDialog()
                        },
                        enabled = selectedFriends.isNotEmpty()) {
                        Text(text = "Send")
                    }
                    TextButton(onClick = { closeDialog() }) {
                        Text(text = "Cancel")
                    }
                }
            }
        }
    }
}

@Composable
fun RemoveParticipantFromEventDialog(
    viewModel: EventDetailViewModel,
    closeDialog: () -> Unit
    ){
    val participants = viewModel.participants.collectAsStateWithLifecycle().value
    val selectedParticipants = remember { mutableStateListOf<User>() }

    viewModel.getParticipants()

    Dialog(
        onDismissRequest = { closeDialog() }){
        Card(modifier = Modifier
            .width(300.dp)
            .height(400.dp)
            .padding(16.dp),
            shape = RoundedCornerShape(16.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                HeaderText(text = "Remove participant")
                Divider()
                LazyColumn(
                    content = {
                    participants.forEach { participant ->
                        Log.d("EventDetailScreen", "Participant: $participant")
                        item {
                            ListItem (
                                headlineText = {
                                    Text(text = participant.username)
                                },
                                leadingContent = {
                                    Icon(imageVector = Icons.Default.Face,
                                        contentDescription = null,
                                        modifier = Modifier.size(25.dp))
                                },
                                trailingContent = {
                                    Checkbox(checked = selectedParticipants.contains(participant), onCheckedChange = {
                                        if (!selectedParticipants.contains(participant)) selectedParticipants.add(participant)
                                        else selectedParticipants.remove(participant)
                                    })
                                })
                        }
                        viewModel.getFriendList()
                    }
                })
                Divider()
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                    TextButton(
                        onClick = {
                            Log.d("EventDetailScreen", "Removed ${selectedParticipants.size} participants")
                            selectedParticipants.forEach { user -> viewModel.removeFromEvent(user.uid) }
                            closeDialog()
                                  },
                        enabled = selectedParticipants.isNotEmpty()) {
                        Text(text = "Remove", color = Color.Red)
                    }
                    TextButton(onClick = { closeDialog() }) {
                        Text(text = "Cancel")
                    }
                }
            }
        }
    }

}

@Composable
fun DeleteEventDialog(
    viewModel: EventDetailViewModel,
    closeDialog: () -> Unit){

    val navController = rememberNavController()
    AlertDialog(
        title = {
                Text(text = "Delete event", color = Color.Red)
        },
        text = {
               Text(text = "Are you sure you wish to delete this event? This action cannot be undone.")
        },
        icon = {
               Icon(imageVector = Icons.Default.Delete, contentDescription = null)
        },
        onDismissRequest = { closeDialog() },
        confirmButton = {
            TextButton(onClick = {
                closeDialog()
                viewModel.deleteEvent()
                navController.previousBackStackEntry
            }) {
                Text(text = "Delete", color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onClick = { closeDialog() }) {
                Text(text = "Cancel")
            }
        }
    )
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