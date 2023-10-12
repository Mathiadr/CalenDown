package no.gruppe02.hiof.calendown.screen

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.CalendarView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.Surface
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
import no.gruppe02.hiof.calendown.R
import no.gruppe02.hiof.calendown.ui.theme.CalenDownTheme
class AddEventScreen : ComponentActivity() {
    var year : Int = 0
    var month : Int = 0
    var day : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.new_app_widget)

        setContent {
            CalenDownTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AddEventScreenApp()
                }
            }
        }
    }


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreenApp() {
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
                    Button(
                        onClick = {},
                        modifier = Modifier.absoluteOffset(x = (-15).dp, y = 0.dp)

                    ) {
                        Text(text = "Exit")
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
        AddEventScreen(Modifier.padding(innerPadding))
    }
}




@SuppressLint("NotConstructor")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(modifier: Modifier = Modifier) {
    val textState = remember { mutableStateOf("") }
    val textState2 = remember { mutableStateOf("") }
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
            CalendarView.OnDateChangeListener {
                    view: CalendarView, i: Int, i1: Int, i2: Int ->
                year = i
                month = i1+1
                day = i2
        }
    }
}}}




