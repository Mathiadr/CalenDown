package no.gruppe02.hiof.calendown.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import no.gruppe02.hiof.calendown.model.Event

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier,
               onEventClick: (String) -> Unit,
               onAddEventClick: () -> Unit,
               viewModel: HomeViewModel = hiltViewModel()) {
    val events = viewModel.events.collectAsStateWithLifecycle(emptyList())


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
        floatingActionButton = {
            OpenAddEventScreen(onAddEventClick)
        }

    ) { innerPadding ->
        Column (
            modifier = Modifier
                .padding(innerPadding)){
            LazyColumn(content = {
                items(events.value, key= { it.uid }) { event ->
                    EventCard(event = event, onEventClick = onEventClick)
                }
            })
        }
    }
}

@Composable
fun OpenAddEventScreen(
    onAddEventClick: () -> Unit
) {
    FloatingActionButton(
        onClick = { onAddEventClick() },
        //contentColor =
    )
    {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Create new event button",
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventCard(
    event: Event,
    onEventClick: (String) -> Unit,
    modifier: Modifier = Modifier) {

    Card (
        onClick = {onEventClick(event.uid)},
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
                imageVector = Icons.Default.Warning,
                contentDescription = null,
                modifier = Modifier.size(50.dp))

            Column {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.headlineSmall)
                Text(
                    text = event.date.toString(),
                    style = MaterialTheme.typography.labelMedium)
            }
            Column {
                Row {
                    Text(
                        text = "4", //TODO: Replace with countdown API
                        style = MaterialTheme.typography.headlineSmall,
                        modifier = Modifier
                            .weight(1f, fill = true)
                            .padding(end = 25.dp),
                        textAlign = TextAlign.End)
                }
                Row {
                    Text(
                        text = "days to go", //TODO: Replace with countdown API
                        style = MaterialTheme.typography.labelMedium,
                        modifier = Modifier.weight(1f, fill = true),
                        textAlign = TextAlign.End)
                }
            }
        }

    }
}
