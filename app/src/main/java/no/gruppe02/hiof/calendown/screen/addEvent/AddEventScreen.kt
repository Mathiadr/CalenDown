package no.gruppe02.hiof.calendown.screen.addEvent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                value = eventName.value,
                onValueChange = {
                    eventName.value = it
                },
                modifier = Modifier.fillMaxWidth()
            )

            if (eventName.value.isEmpty()) {
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
                value = eventDescription.value,
                onValueChange = {
                    eventDescription.value = it
                },
                modifier = Modifier.fillMaxWidth()
            )

            if (eventDescription.value.isEmpty()) {
                Text(
                    text = "Enter event description",
                    color = Color.Gray,
                    modifier = Modifier.padding(15.dp)
                )
            }
        }
        Button(
            onClick = {
                mDatePickerDialog.show()
            },
            modifier = Modifier
                .absoluteOffset(x = 315.dp, y = 30.dp)

        ) {
            Text(text = "Date")
        }
        Button(
            onClick = {
                mTimePickerDialog.show()
            },
            modifier = Modifier
                .absoluteOffset(x = 315.dp, y = 30.dp)

        ) {
            Text(text = "Time")
        }
        Button(
            onClick = {
                val dateFormat = SimpleDateFormat("dd/MM/yyyy:hh:mm")
                val dateObject = dateFormat.parse(selectedDate.value + mTime.value)
                viewModel.saveEvent(
                    eventName = eventName.value,
                    eventDescription = eventDescription.value,
                    eventDate = dateObject)
                onSaveEventClick()
            },
            modifier = Modifier
                .absoluteOffset(x = 315.dp, y = 0.dp)

        ) {
            Text(text = "Save")
        }

}

}

