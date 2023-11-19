package no.gruppe02.hiof.calendown.screen.addEvent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.text.SimpleDateFormat
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
    val selectedDate = remember { mutableStateOf("") }
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
    val mTime = remember { mutableStateOf("") }

    val mTimePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            mTime.value = ":$mHour:$mMinute"
        }, mHour, mMinute, false
    )

    mCalendar.time = Date()
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            selectedDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
        }, mYear, mMonth, mDay
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Event information",
            style = MaterialTheme.typography.headlineSmall,
        )
        OutlinedTextField(
            singleLine = true,
            modifier = modifier
                .fillMaxWidth(),
            value = eventName.value,
            onValueChange = { eventName.value = it },
            label = { Text(text = "Enter event name") },
        )
        // TODO: Select icon

        OutlinedTextField(
            singleLine = false,
            modifier = modifier
                .fillMaxWidth(),
            value = eventDescription.value,
            onValueChange = { eventDescription.value = it },
            label = { Text(text = "Describe your event (optional)") },
        )

        OutlinedButton(
            onClick = {
                mDatePickerDialog.show()
            },
            modifier = Modifier
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
                .fillMaxSize()
                //.size(200.dp, 50.dp)

        ) {
            Icon(imageVector = Icons.Default.DateRange, contentDescription = "pick event time")
            Text(text = "Choose Time of Event")
        }
        Row(horizontalArrangement = Arrangement.End){
            FilledTonalButton(
                onClick = {
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy:hh:mm")
                    val dateObject = dateFormat.parse(selectedDate.value + mTime.value)
                    viewModel.saveEvent(
                        eventName = eventName.value,
                        eventDescription = eventDescription.value,
                        eventDate = dateObject)
                    onSaveEventClick()
                },

                ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "save event")
                Text(text = "Save")
            }
        }
    }
}


