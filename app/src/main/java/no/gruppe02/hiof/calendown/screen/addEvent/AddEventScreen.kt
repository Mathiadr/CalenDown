package no.gruppe02.hiof.calendown.screen.addEvent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import no.gruppe02.hiof.calendown.components.HeaderText
import java.text.SimpleDateFormat
import java.time.Instant
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
            TopAppBar( title = {
                Text(
                    text = "Create Event",
                    style = MaterialTheme.typography.displaySmall,
                )},
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ){
            AddEventScreenContent(onSaveEventClick, Modifier.padding(innerPadding))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreenContent(
    onSaveEventClick: () -> Unit,
    modifier: Modifier = Modifier, viewModel: AddEventViewModel = hiltViewModel()) {
    val eventName = remember { mutableStateOf("") }
    val eventDescription = remember { mutableStateOf("") }
    val selectedIcon = remember { mutableStateOf<ImageVector?>(viewModel.icons[Icons.Default.DateRange.name]) }


    //time and date defaults to now if left empty
    val currentTime: LocalTime = LocalTime.now()
    val today: LocalDate = LocalDate.now()
    val formattedDate: String = today.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    val formattedTime: String = currentTime.format(DateTimeFormatter.ofPattern(":HH:mm"))
    val selectedDate = remember { mutableStateOf(formattedDate) }
    val mTime = remember { mutableStateOf(formattedTime) }

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
        {_, mHour : Int, mMinute: Int ->
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
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Event information",
            style = MaterialTheme.typography.headlineSmall,
        )

        Row(horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()) {
            OutlinedTextField(
                singleLine = true,
                modifier = modifier,
                value = eventName.value,
                onValueChange = { eventName.value = it },
                label = { Text(text = "Enter event name") },
            )
            IconSelectionBox(viewModel = viewModel, selectedIcon = selectedIcon)
        }

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

        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.fillMaxSize(),
        )

        Row(horizontalArrangement = Arrangement.End){
            val dateFormat = SimpleDateFormat("dd/MM/yyyy:hh:mm")
            val dateObject = dateFormat.parse(selectedDate.value + mTime.value)

            FilledTonalButton(
                enabled = Date.from(Instant.now()).before(dateObject),
                onClick = {
                    viewModel.saveEvent(
                        name = eventName.value,
                        description = eventDescription.value,
                        date = dateObject as Date,
                        icon = selectedIcon.value?.name
            )

                    scope.launch {
                        delay(400)
                        snackbarHostState.showSnackbar("Event created")
                        onSaveEventClick()
                    }
                },

                ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "save event")
                Text(text = "Save")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IconSelectionBox(viewModel: AddEventViewModel, selectedIcon: MutableState<ImageVector?>){
    var expanded by remember { mutableStateOf(false) }
    val icons = viewModel.icons
    if(selectedIcon.value == null) selectedIcon.value = Icons.Default.DateRange

    Box(modifier = Modifier
        .padding(6.dp)
        .background(MaterialTheme.colorScheme.primaryContainer)
        .width(60.dp)
        .clickable(
            enabled = true,
            onClick = {expanded = true}
            )){
        Icon(
            imageVector = selectedIcon.value!!,
            contentDescription = selectedIcon.value!!.name,
            tint = MaterialTheme.colorScheme.onPrimaryContainer)
        if (expanded) {
            Dialog(
                onDismissRequest = { expanded = false }){
                Surface(modifier = Modifier
                    .width(300.dp)
                    .height(400.dp)
                    .padding(16.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column (
                        modifier = Modifier
                            .padding()
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.Top),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        HeaderText(text = "Select icon")
                        Divider()
                        LazyRow(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(6.dp),
                            content = {
                            icons.forEach{ icon ->
                                item {
                                    Card(
                                        modifier = Modifier.padding(4.dp),
                                        onClick = {
                                            selectedIcon.value = icon.value
                                            expanded = false
                                        },
                                        shape = RoundedCornerShape(16.dp)) {
                                        Icon(imageVector = icon.value, contentDescription = icon.key )
                                    }
                                }
                            }
                        })
                    }
                }
            }
        }
    }
}




