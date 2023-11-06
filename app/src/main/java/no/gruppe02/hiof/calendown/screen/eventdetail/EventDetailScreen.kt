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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import no.gruppe02.hiof.calendown.dummydata.DefaultIcons
import java.text.SimpleDateFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(modifier: Modifier = Modifier,
                      viewModel: EventDetailViewModel = hiltViewModel()
) {
    val event by viewModel.event
    val dateString = SimpleDateFormat.getDateTimeInstance().format(event.date)
    val years = viewModel.getRemainingYears()
    val months = viewModel.getRemainingMonths()
    val days = viewModel.getRemainingDays()
    val hours = viewModel.getRemainingHours()
    val minutes = viewModel.getRemainingMinutes()
    val seconds = viewModel.getRemainingSeconds()


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
            Text(
                text = "id: " + event.uid,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = event.title,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = "Owned by " + event.userId,
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = event.description,
                style = MaterialTheme.typography.bodyMedium
            )
            if (event.participants?.isNotEmpty() == true){
                Column {
                    Text(
                        text = "participants:",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    event.participants?.forEach {participant ->
                        Text(
                            text = participant,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
            Text(
                text = dateString,
                style = MaterialTheme.typography.bodyLarge
            )
            Row(horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 6.dp)) {
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
        }
            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)) {
            Icon(imageVector = Icons.Default.Warning, contentDescription = null)
            Text(text = event.title,
                style = MaterialTheme.typography.headlineLarge)
            Text(text = event.description,
                style = MaterialTheme.typography.bodyMedium)
            Text(text = event.date.toString(),
                style = MaterialTheme.typography.bodyMedium)
            Column {
                Text(text = "Years: $years",
                    style = MaterialTheme.typography.bodyMedium)
                Text(text = "Months: $months",
                    style = MaterialTheme.typography.bodyMedium)
                Text(text = "Days: $days",
                    style = MaterialTheme.typography.bodyMedium)
                Text(text = "Hours: $hours",
                    style = MaterialTheme.typography.bodyMedium)
                Text(text = "Minutes: $minutes",
                    style = MaterialTheme.typography.bodyMedium)
                Text(text = "Seconds: $seconds",
                    style = MaterialTheme.typography.bodyMedium)
            }


        /* TODO: Implement
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(event.image)
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(16.dp).width(400.dp).height(600.dp)
        )
        */
    }
    }



}


@Composable
@Preview
fun EventDetailScreenPreview(){
    CalenDownTheme {
        EventDetailScreen()
    }
}