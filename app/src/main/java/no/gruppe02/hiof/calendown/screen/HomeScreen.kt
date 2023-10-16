package no.gruppe02.hiof.calendown.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController

// Dummydata for å vise et kort
sealed class Event(
    val title: String,
    val date: String,
    val icon: ImageVector,
    val countdown: String)

{
    data object event : Event(
        "Event title",
        "Mandag 24. januar 2023, 14:00",
        Icons.Outlined.Face,
        "4")

}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(onEventClick: () -> Unit ) {
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
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                )
            )
        },
    ) { innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)){
            EventCard(Event.event, onEventClick)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventCard(
    event: Event,
    onEventClick: () -> Unit,
    modifier: Modifier = Modifier) {

    Card (
        onClick = {onEventClick()},
        modifier
            .fillMaxWidth()
            .padding(10.dp)
            .padding(top = 6.dp)
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
                imageVector = event.icon,
                contentDescription = null,
                modifier = Modifier.size(50.dp))

            Column {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.headlineSmall)
                Text(
                    text = event.date,
                    style = MaterialTheme.typography.labelMedium)
            }
            Column {
                Row {
                    Text(
                        text = event.countdown,
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .weight(1f, fill = true)
                            .padding(end = 25.dp),
                        textAlign = TextAlign.End)
                }
                Row {
                    Text(
                        text = "days to go",
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.weight(1f, fill = true),
                        textAlign = TextAlign.End)
                }
            }
        }

    }
}
