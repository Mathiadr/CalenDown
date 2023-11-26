@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package no.gruppe02.hiof.calendown.screen.eventdetail

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import no.gruppe02.hiof.calendown.components.BasicContainer
import no.gruppe02.hiof.calendown.components.BasicScreenLayout
import no.gruppe02.hiof.calendown.components.HeaderText
import no.gruppe02.hiof.calendown.components.ProfileImage
import no.gruppe02.hiof.calendown.data.EventIcons
import no.gruppe02.hiof.calendown.model.EventTimer
import no.gruppe02.hiof.calendown.model.User
import java.text.SimpleDateFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(viewModel: EventDetailViewModel = hiltViewModel())
{
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
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                actions = {
                    EventDropdownMenu(viewModel, snackbarHostState)
                }
            )

        }

    ) { innerPadding ->

        BasicScreenLayout(
            innerPadding = innerPadding,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            GenericInformation(viewModel)
            Participants(viewModel = viewModel)
        }
    }
}

@Composable
fun GenericInformation(viewModel: EventDetailViewModel){
    val event = viewModel.event.value
    val dateString = SimpleDateFormat.getDateTimeInstance().format(event.date)
    val owner = viewModel.owner.value

    BasicContainer {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Event",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.width(6.dp))
            Divider()
        }
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Top) {
            Icon(
                imageVector = EventIcons.DefaultIcons.defaultIcons.getOrDefault(
                    event.icon,
                    Icons.Default.DateRange
                ),
                tint = MaterialTheme.colorScheme.surfaceTint,
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = dateString,
                    style = MaterialTheme.typography.headlineSmall
                )
                Text(
                    text = "by ${owner.username}",
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
        Divider()
        Timer(eventTimer = viewModel.eventTimer.value)
    }

    BasicContainer {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            value = event.description.ifEmpty { "This event does not contain a description" },
            onValueChange = {},
            readOnly = true,
            label = { Text(text = "Event description") }
        )
    }
}

@Composable
fun Participants(viewModel: EventDetailViewModel){
    val participants = viewModel.participants.collectAsStateWithLifecycle().value
    val participantProfileImages = viewModel.userImages.toMap()
    viewModel.getParticipants()

    BasicContainer {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Participants of this event",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.width(6.dp))
            Divider()
        }
        LazyColumn(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(6.dp, 0.dp),
            userScrollEnabled = true,
            content = {
                participants.forEach { user ->
                    item {
                        Participant(
                            participant = user,
                            participantProfileImageUri = participantProfileImages[user.uid])
                    }
                }
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Participant(participant: User, participantProfileImageUri: Uri?){
    ListItem (headlineText = {
        Text(text = participant.username)
    },
        leadingContent = {
            ProfileImage(
                imageUrl = participantProfileImageUri,
                modifier = Modifier.size(50.dp)
            )
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
    var openLeaveEventDialog by remember { mutableStateOf(false)}

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
                        openLeaveEventDialog = true
                    }
                )
            }
        }
    }
    if (openInviteDialog)
        InviteToEventDialog(
            viewModel = viewModel,
            closeDialog = {
                openInviteDialog = false
                expanded = false
            }
        )
    if (openDeleteEventDialog)
        DeleteEventDialog(
            viewModel = viewModel,
            closeDialog = {
                openDeleteEventDialog = false
                expanded = false
            }
        )
    if (openRemoveParticipantDialog)
        RemoveParticipantFromEventDialog(
            viewModel = viewModel,
            closeDialog = {
                openRemoveParticipantDialog = false
                expanded = false
            }
        )
    if (openLeaveEventDialog)
        LeaveEventDialog(
            viewModel = viewModel,
            closeDialog = {
                openLeaveEventDialog = false
                expanded = false
            }
        )

}


// https://developer.android.com/reference/kotlin/androidx/compose/material3/package-summary#AlertDialog(kotlin.Function0,kotlin.Function0,androidx.compose.ui.Modifier,kotlin.Function0,kotlin.Function0,kotlin.Function0,kotlin.Function0,androidx.compose.ui.graphics.Shape,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,androidx.compose.ui.graphics.Color,androidx.compose.ui.unit.Dp,androidx.compose.ui.window.DialogProperties)
@Composable
fun InviteToEventDialog(
    viewModel: EventDetailViewModel,
    closeDialog: () -> Unit
    ){
    val friends = viewModel.friendList.collectAsStateWithLifecycle()
    val selectedFriends = remember { mutableStateListOf<User>() }
    val friendProfileImages = viewModel.userImages.toMap()
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
                                    ProfileImage(
                                        imageUrl = friendProfileImages[friend.uid],
                                        modifier = Modifier.size(50.dp)
                                        )
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
fun LeaveEventDialog(
    viewModel: EventDetailViewModel,
    closeDialog: () -> Unit){

    val navController = rememberNavController()
    AlertDialog(
        title = {
            Text(text = "Leave event", color = Color.Red)
        },
        text = {
            Text(text = "Are you sure you wish to leave this event? You can rejoin this event if you receive a new invite.")
        },
        icon = {
            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
        },
        onDismissRequest = { closeDialog() },
        confirmButton = {
            TextButton(onClick = {
                closeDialog()
                viewModel.removeFromEvent(viewModel.currentUserId)
                navController.previousBackStackEntry
            }) {
                Text(text = "Leave", color = Color.Red)
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


    val countStyle = MaterialTheme.typography.headlineMedium
    val countFont = null
    val countColor = MaterialTheme.colorScheme.primary
    val labelStyle = MaterialTheme.typography.headlineSmall
    val labelFont = null
    val labelColor = MaterialTheme.colorScheme.primary
    val timerArrangement = Arrangement.Top
    val timerAlignment = Alignment.CenterHorizontally
    
    Column(modifier = Modifier
        .fillMaxWidth()
        .wrapContentHeight(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (years != "0" || months != "0" || days != "0") {
            Row(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.Top) {
                Column(verticalArrangement = timerArrangement,
                    horizontalAlignment = timerAlignment){
                    Text(
                        text = years,
                        style = countStyle,
                        color = countColor,
                        modifier = Modifier
                            .padding(end = 5.dp),
                        textAlign = TextAlign.End)
                    Text(
                        text = "years",
                        style = labelStyle,
                        color = labelColor,
                        modifier = Modifier
                            .padding(end = 2.dp),
                        textAlign = TextAlign.End)
                }
                Column(verticalArrangement = timerArrangement,
                    horizontalAlignment = timerAlignment){
                    Text(
                        text = months,
                        style = countStyle,
                        color = countColor,
                        modifier = Modifier
                            .padding(end = 5.dp),
                        textAlign = TextAlign.End)
                    Text(
                        text = "months",
                        style = labelStyle,
                        color = labelColor,
                        modifier = Modifier
                            .padding(end = 2.dp),
                        textAlign = TextAlign.End)
                }
                Column(verticalArrangement = timerArrangement,
                    horizontalAlignment = timerAlignment){
                    Text(
                        text = days,
                        style = countStyle,
                        color = countColor,
                        modifier = Modifier
                            .padding(end = 5.dp),
                        textAlign = TextAlign.End)
                    Text(
                        text = "days",
                        style = labelStyle,
                        color = labelColor,
                        modifier = Modifier
                            .padding(end = 2.dp),
                        textAlign = TextAlign.End)
                }
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Top) {
            Column(verticalArrangement = timerArrangement,
                horizontalAlignment = timerAlignment){
                Text(
                    text = hours,
                    style = countStyle,
                    color = countColor,
                    modifier = Modifier
                        .padding(end = 5.dp),
                    textAlign = TextAlign.End)
                Text(
                    text = "hours",
                    style = labelStyle,
                    color = labelColor,
                    modifier = Modifier
                        .padding(end = 2.dp),
                    textAlign = TextAlign.End)
            }
            Column(verticalArrangement = timerArrangement,
                horizontalAlignment = timerAlignment){
                Text(
                    text = minutes,
                    style = countStyle,
                    color = countColor,
                    modifier = Modifier
                        .padding(end = 5.dp),
                    textAlign = TextAlign.End)
                Text(
                    text = "minutes",
                    style = labelStyle,
                    color = labelColor,
                    modifier = Modifier
                        .padding(end = 2.dp),
                    textAlign = TextAlign.End)
            }
            Column(verticalArrangement = timerArrangement,
                horizontalAlignment = timerAlignment){
                Text(
                    text = seconds,
                    style = countStyle,
                    color = countColor,
                    modifier = Modifier
                        .padding(end = 5.dp),
                    textAlign = TextAlign.End
                )
                Text(
                    text = "seconds",
                    style = labelStyle,
                    color = labelColor,
                    modifier = Modifier
                        .padding(end = 2.dp),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}