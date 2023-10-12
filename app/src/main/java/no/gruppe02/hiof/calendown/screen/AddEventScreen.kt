package no.gruppe02.hiof.calendown.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                {
                    Button(
                        onClick = {},
                        modifier = Modifier
                            .absoluteOffset(x = 315.dp, y = 0.dp)

                    ) {
                        Text(text = "Save")
                    }

                    Text(
                        text = "Create Event",
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.absoluteOffset(x = 80.dp, y = 0.dp)
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                )
            )
        },
    ) { innerPadding ->
        AddEventScreenContent(Modifier.padding(innerPadding))
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreenContent(modifier: Modifier = Modifier) {
    val textState = remember { mutableStateOf("") }
    val textState2 = remember { mutableStateOf("") }
    val date = remember { mutableStateOf("") }
    var checkedState by rememberSaveable { mutableStateOf(false)}


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Event information",
                style = MaterialTheme.typography.headlineSmall
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .background(color = Color.LightGray)
        ) {
            TextField(
                value = textState2.value,
                onValueChange = {
                    textState2.value = it
                },
                modifier = Modifier.fillMaxWidth()
            )

            if (textState2.value.isEmpty()) {
                Text(
                    text = "Enter eventname here",
                    color = Color.Gray,
                    modifier = Modifier.padding(15.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .background(color = Color.LightGray)
        ) {
            TextField(
                value = textState.value,
                onValueChange = {
                    textState.value = it
                },
                modifier = Modifier.fillMaxWidth()
            )

            if (textState.value.isEmpty()) {
                Text(
                    text = "Enter event description",
                    color = Color.Gray,
                    modifier = Modifier.padding(15.dp)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(6.dp)
                .background(color = Color.LightGray)
        ) {
            TextField(
                value = date.value,
                onValueChange = {
                    date.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
            )

            if (date.value.isEmpty()) {
                Text(
                    text = "Event date: 2018.09.18",
                    color = Color.Gray,
                    modifier = Modifier.padding(15.dp)
                )
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.LightGray),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Repeat yearly?",
                modifier = Modifier.padding(15.dp)

            )
            Checkbox(
                checked = checkedState,
                onCheckedChange = { newCheckedState ->
                    checkedState = newCheckedState
                })
    }
}}
