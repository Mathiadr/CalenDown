package no.gruppe02.hiof.calendown.screen.eventdetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dagger.hilt.android.lifecycle.HiltViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.type.Date
import no.gruppe02.hiof.calendown.CalendownApp
import no.gruppe02.hiof.calendown.model.Event
import no.gruppe02.hiof.calendown.ui.theme.CalenDownTheme


@Composable
fun EventDetailScreen(modifier: Modifier = Modifier

) {
    // Dummy data
    val event = Event(
        uid = "1",
        userId = "Ola Nordmann",
        title = "Event title",
        description = "Event Description",
        date = "Mandag 24. januar 2023, 14:00"

    )
    val countdown = "4 Hours"

    // val event by viewModel.event

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize()) {
        Icon(imageVector = Icons.Outlined.Face, contentDescription = null)
        Text(text = event.title,
            style = MaterialTheme.typography.headlineLarge)
        Text(text = event.description,
            style = MaterialTheme.typography.bodyMedium)
        Text(text = event.date,
            style = MaterialTheme.typography.bodyMedium)
        Text(text = countdown,
            style = MaterialTheme.typography.bodyLarge)
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

@Composable
@Preview
fun EventDetailScreenPreview(){
    CalenDownTheme {
        EventDetailScreen()
    }
}