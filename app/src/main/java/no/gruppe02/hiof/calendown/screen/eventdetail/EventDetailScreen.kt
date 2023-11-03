package no.gruppe02.hiof.calendown.screen.eventdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
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
import no.gruppe02.hiof.calendown.ui.theme.CalenDownTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(modifier: Modifier = Modifier,
                      viewModel: EventDetailViewModel = hiltViewModel()
) {
    val event by viewModel.event
    val remainingTimeLong = viewModel.remainingTimeLong
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
                        text = "Event",
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