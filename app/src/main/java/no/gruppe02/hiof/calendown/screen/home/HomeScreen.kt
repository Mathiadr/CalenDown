package no.gruppe02.hiof.calendown.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import no.gruppe02.hiof.calendown.dummydata.DefaultIcons
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
                    DeleteAll(onDelete = { viewModel.deleteAll()})
                },

                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                )
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
fun DeleteAll(
    onDelete: () -> Unit
) {
    Button(
        onClick = { onDelete() }
    )
    {
        Icon(
            imageVector = Icons.Default.Delete,
            contentDescription = "Delete All",
            tint = Color.Red
        )
    }
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
        onClick = {onEventClick(event.uid)},
        modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { },

    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier
                .padding(10.dp)
                .padding(top = 6.dp, bottom = 6.dp)


        ) {
            Icon(
                imageVector = DefaultIcons.defaultIcons.getOrDefault(
                    event.icon,
                    Icons.Default.ThumbUp
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
