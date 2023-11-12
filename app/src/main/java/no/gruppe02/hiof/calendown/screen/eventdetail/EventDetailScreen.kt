package no.gruppe02.hiof.calendown.screen.eventdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import no.gruppe02.hiof.calendown.dummydata.DefaultIcons
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
    val years = eventTimer.years.value
    val months = eventTimer.months.value
    val days = eventTimer.days.value
    val hours = eventTimer.hours.value
    val minutes = eventTimer.minutes.value
    val seconds = eventTimer.seconds.value


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
            IconButton(
                onClick = {
                          viewModel.deleteEvent()
                },
                modifier = Modifier
                    .absoluteOffset(x = 10.dp, y = 10.dp)
            ) {
                Icon(imageVector = Icons.Rounded.Delete, contentDescription = "Delete event",
                    modifier = Modifier
                        .size(50.dp, 50.dp))

            }

        },

    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Icon(
                imageVector = DefaultIcons.defaultIcons.getOrDefault(
                    event.icon,
                    Icons.Default.ThumbUp
                ),
                contentDescription = null,
                modifier = Modifier.size(100.dp)
            )
            GenericInformation(
                title = event.title, 
                owner = event.userId, 
                description = event.description, 
                date = dateString
            )
            if (event.participants?.isNotEmpty() == true){
                Participants(participants = event.participants!!)
            }
            Row(horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)) {
                Timer(
                    years = years,
                    months = months,
                    days = days,
                    hours = hours,
                    minutes = minutes,
                    seconds = seconds
                )
            }
        }
    }
}

@Composable
fun GenericInformation(title: String, date: String, owner: String, description: String){
    Text(text = title)
    Text(text = date)
    Text(text = owner)
    Text(text = description)
}

@Composable
fun Participants(participants: List<String>){
    participants.forEach { 
        Participant(participant = it)
    }
}

@Composable
fun Participant(participant: String){
    Text(text = participant)
}

@Composable
fun Timer(years: String,
          months: String,
          days: String,
          hours: String,
          minutes: String,
          seconds: String){
    Column(verticalArrangement = Arrangement.Center,
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