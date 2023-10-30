package no.gruppe02.hiof.calendown.screen.eventdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import no.gruppe02.hiof.calendown.api.getDays
import no.gruppe02.hiof.calendown.api.getHours
import no.gruppe02.hiof.calendown.api.getMinutes
import no.gruppe02.hiof.calendown.api.getSeconds


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventDetailScreen(modifier: Modifier = Modifier,
                      viewModel: EventDetailViewModel = hiltViewModel()
) {

    val event by viewModel.event
    val remainingTimeLong = viewModel.remainingTimeLong
    val days = getDays(remainingTimeLong.longValue).toString()
    val hours = getHours(remainingTimeLong.longValue).toString()
    val minutes = getMinutes(remainingTimeLong.longValue).toString()
    val seconds = getSeconds(remainingTimeLong.longValue).toString()


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = event.title,
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