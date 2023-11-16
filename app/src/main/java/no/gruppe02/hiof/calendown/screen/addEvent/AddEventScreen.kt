package no.gruppe02.hiof.calendown.screen.addEvent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    onSaveEventClick: () -> Unit
) {

    Scaffold(

        topBar = {
            TopAppBar(
                {
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
        AddEventScreenContent(onSaveEventClick, Modifier.padding(innerPadding))
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreenContent(

    onSaveEventClick: () -> Unit,
    modifier: Modifier = Modifier, viewModel: AddEventViewModel = hiltViewModel()) {
    val eventName = remember { mutableStateOf("") }
    val eventDescription = remember { mutableStateOf("") }


    //time and date defaults to now if left empty
    val currentTime: LocalTime = LocalTime.now()
    val today: LocalDate = LocalDate.now()
    val formattedDate: String = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    val formattedTime: String = currentTime.format(DateTimeFormatter.ofPattern(":HH:mm"))
    val selectedDate = remember { mutableStateOf("$formattedDate") }
    val mTime = remember { mutableStateOf("$formattedTime") }


    val mContext = LocalContext.current
    val mYear: Int
    val mMonth: Int
    val mDay: Int
    val mCalendar = Calendar.getInstance()
    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]


    val mTimePickerDialog = TimePickerDialog(
        mContext,
        { _, mHour: Int, mMinute: Int ->
            mTime.value = ":$mHour:$mMinute"
        }, mHour, mMinute, false
    )

    mCalendar.time = Date()
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            selectedDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, mYear, mMonth, mDay
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Event information",
            modifier = Modifier.absoluteOffset(5.dp, 0.dp),
            style = MaterialTheme.typography.headlineSmall,
        )
        OutlinedTextField(
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .absoluteOffset(0.dp, (-50).dp),
            value = eventName.value,
            onValueChange = { eventName.value = it },
            label = { Text(text = "Enter eventname") },
        )

        OutlinedTextField(
            singleLine = true,
            modifier = modifier
                .fillMaxWidth()
                .absoluteOffset(0.dp, (-100).dp),
            value = eventDescription.value,
            onValueChange = { eventDescription.value = it },
            label = { Text(text = "Enter eventname") },
        )

        OutlinedButton(
            onClick = {
                mDatePickerDialog.show()
            },
            modifier = Modifier
                .absoluteOffset(0.dp, (-70).dp)
                .fillMaxSize()
            //.size(200.dp, 50.dp)
        ) {
            Icon(imageVector = Icons.Default.DateRange, contentDescription = "pick event date")
            Text(text = "Choose Date of Event")
        }
        OutlinedButton(
            onClick = {
                mTimePickerDialog.show()
            },
            modifier = Modifier
                .absoluteOffset(0.dp, (-60).dp)
                .fillMaxSize()
            //.size(200.dp, 50.dp)

        ) {
            Icon(imageVector = Icons.Default.DateRange, contentDescription = "pick event time")
            Text(text = "Choose Time of Event")
        }


        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.fillMaxSize()
        )


        FilledTonalButton(
            onClick = {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy:hh:mm")
                val dateObject = dateFormat.parse(selectedDate.value + mTime.value)
                scope.launch {
                    delay(0)
                    snackbarHostState.showSnackbar("Snackbar")
                }
                viewModel.saveEvent(
                    eventName = eventName.value,
                    eventDescription = eventDescription.value,
                    eventDate = dateObject
                )
                onSaveEventClick()
            },
            modifier = Modifier
                .absoluteOffset(240.dp, 60.dp)
                .size(120.dp, 50.dp)
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "save event")
            Text(text = "Save")
        }

        }

    }


