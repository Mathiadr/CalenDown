package no.gruppe02.hiof.calendown.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import no.gruppe02.hiof.calendown.datasource.EventIcons
import no.gruppe02.hiof.calendown.model.Event
import no.gruppe02.hiof.calendown.model.EventTimer
import java.text.SimpleDateFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               onEventClick: (String) -> Unit,
               onAddEventClick: () -> Unit,
               viewModel: HomeViewModel = hiltViewModel()) {
    val activeEvents by viewModel.activeEvents.collectAsStateWithLifecycle(emptyMap())

    Scaffold(
        topBar = {
            TopAppBar(
                {
                    Text(
                        text = "Events",
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                actions = { OptionsDropdownMenu(viewModel = viewModel) }
            )

        },
        floatingActionButton = {
            OpenAddEventScreen(onAddEventClick)
        }

    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column (
                modifier = Modifier
                    .padding()
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(18.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally) {
                LazyColumn(

                    content = {
                        activeEvents.entries.forEach{ entry ->
                            item(entry.key.uid) {
                                EventCard(
                                    event = entry.key,
                                    eventTimer = entry.value,
                                    onEventClick = onEventClick)
                            }
                        }
                    }
                )
            }
        }
    }
}



@Composable
fun OpenAddEventScreen(
    onAddEventClick: () -> Unit
) {
    FloatingActionButton(
        onClick = { onAddEventClick() }
    )
    {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Create new event button",
        )
    }
}

@Composable
fun OptionsDropdownMenu(viewModel: HomeViewModel){
    var expanded by remember { mutableStateOf(false) }
    var openDeleteEventsDialog by remember { mutableStateOf(false)}
    Box(modifier = Modifier
        .wrapContentSize()) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "Actions for all events")
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }) {

            // This option is for demonstration purposes only, and not meant to be included in production
            DropdownMenuItem(
                text = { Text(text = "(DEMONSTRATION) Populate with dummies", color = Color.Blue) },
                onClick = {
                    viewModel.debugPopulateWithDummies()
                    expanded = false
                }
            )

            DropdownMenuItem(
                text = { Text(text = "Delete all events", color = Color.Red) },
                onClick = {
                    openDeleteEventsDialog = true
                    expanded = false
                }
            )

        }
    }

    if (openDeleteEventsDialog){
        DeleteEventsDialog(
            viewModel = viewModel,
            closeDialog = { openDeleteEventsDialog = false })
    }
}

@Composable
fun DeleteEventsDialog(
    viewModel: HomeViewModel,
    closeDialog: () -> Unit
) {
    AlertDialog(
        title = {
            Text(text = "Delete All Events", color = Color.Red)
        },
        text = {
            Column {
                Text(text = "Are you sure you wish to delete all of your events? This action cannot be undone.")
                Text(
                    text = "This will also make you leave events you participate in.",
                    style = MaterialTheme.typography.bodySmall,
                    fontStyle = FontStyle.Italic,
                    color = Color.Gray
                )
            }
        },
        icon = {
            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
        },
        onDismissRequest = { closeDialog() },
        confirmButton = {
            TextButton(onClick = {
                closeDialog()
                viewModel.deleteAll()}) {
                Text(text = "Delete All", color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onClick = { closeDialog() }) {
                Text(text = "Cancel")
            }
        })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventCard(
    event: Event,
    eventTimer: EventTimer,
    onEventClick: (String) -> Unit,
    modifier: Modifier = Modifier) {

    val dateString = SimpleDateFormat.getDateTimeInstance().format(event.date)
    val seconds = eventTimer.seconds.value
    val minutes = eventTimer.minutes.value
    val hours = eventTimer.hours.value
    val days = eventTimer.days.value
    val months = eventTimer.months.value
    val years = eventTimer.years.value


    Card (
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 6.dp
        ),
        onClick = {onEventClick(event.uid)},
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier
                .padding(10.dp)
                .padding(top = 6.dp, bottom = 6.dp)


        ) {
            Icon(
                imageVector = EventIcons.DefaultIcons.defaultIcons.getOrDefault(
                    event.icon,
                    Icons.Default.DateRange
                ),
                contentDescription = null,
                modifier = Modifier.size(50.dp))

            Column {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.headlineSmall)
                Text(
                    text = dateString,
                    style = MaterialTheme.typography.labelMedium)
            }

        }
        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 6.dp)) {
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    text = years,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(end = 5.dp),
                    textAlign = TextAlign.End)
                Text(
                    text = "years",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(end = 2.dp),
                    textAlign = TextAlign.End)
            }
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    text = months,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(end = 5.dp),
                    textAlign = TextAlign.End)
                Text(
                    text = "months",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(end = 2.dp),
                    textAlign = TextAlign.End)
            }
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    text = days,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(end = 5.dp),
                    textAlign = TextAlign.End)
                Text(
                    text = "days",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(end = 2.dp),
                    textAlign = TextAlign.End)
            }
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    text = hours,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(end = 5.dp),
                    textAlign = TextAlign.End)
                Text(
                    text = "hours",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(end = 2.dp),
                    textAlign = TextAlign.End)
            }
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    text = minutes,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(end = 5.dp),
                    textAlign = TextAlign.End)
                Text(
                    text = "minutes",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(end = 2.dp),
                    textAlign = TextAlign.End)
            }
            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(
                    text = seconds,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier
                        .padding(end = 5.dp),
                    textAlign = TextAlign.End)
                Text(
                    text = "seconds",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(end = 2.dp),
                    textAlign = TextAlign.End)
            }
        }
    }
}
