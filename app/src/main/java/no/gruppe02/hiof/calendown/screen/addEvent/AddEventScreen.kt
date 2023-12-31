package no.gruppe02.hiof.calendown.screen.addEvent

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import no.gruppe02.hiof.calendown.R
import no.gruppe02.hiof.calendown.components.BasicScreenLayout
import no.gruppe02.hiof.calendown.components.HeaderText
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreen(
    onSaveEventClick: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }


    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.fillMaxSize(),
                ) },
        topBar = {
            TopAppBar( title =
            {
                Text(
                    text = "Create Event",
                    style = MaterialTheme.typography.displaySmall,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            },
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
    ) { innerPadding ->
        BasicScreenLayout(
            innerPadding = innerPadding,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            AddEventScreenContent(
                onSaveEventClick = onSaveEventClick,
                snackbarHostState = snackbarHostState,
                scope = scope
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEventScreenContent(
    onSaveEventClick: () -> Unit,
    viewModel: AddEventViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState,
    scope: CoroutineScope) {
    val eventName = remember { mutableStateOf("") }
    val eventDescription = remember { mutableStateOf("") }
    val selectedIcon = remember { mutableStateOf(viewModel.icons[Icons.Default.DateRange.name]) }


    //time and date defaults to now if left empty
    val currentTime: LocalTime = LocalTime.now()
    val today: LocalDate = LocalDate.now()

    val selectedDate = remember { mutableStateOf("") }
    val mTime = remember { mutableStateOf("") }

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




    //logikk for timepicker https://www.geeksforgeeks.org/timepicker-in-android/
    val mTimePickerDialog = TimePickerDialog(
        mContext,
        {_, mHour : Int, mMinute: Int ->
            mTime.value = ":$mHour:$mMinute"
        }, mHour, mMinute, true
    )

    // logikk for datepicker https://www.geeksforgeeks.org/date-picker-in-android-using-jetpack-compose/
    mCalendar.time = Date()
    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            selectedDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
        }, mYear, mMonth, mDay
)

    Column(verticalArrangement = Arrangement.spacedBy(25.dp)) {

        Column {
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(0.75f),
                    value = eventName.value,
                    onValueChange = { eventName.value = it },
                    label = { Text(text = stringResource(R.string.enter_event_name)) },
                )
                IconSelectionBox(viewModel = viewModel, selectedIcon = selectedIcon)
            }


            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                singleLine = false,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                value = eventDescription.value,
                onValueChange = { eventDescription.value = it },
                label = { Text(text = stringResource(R.string.describe_your_event_optional)) },
            )
        }

        Column {
            OutlinedButton(
                onClick = {
                    mDatePickerDialog.show()
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "pick event date")
                Text(text = selectedDate.value.ifEmpty { stringResource(R.string.select_date) } )
            }

            OutlinedButton(
                onClick = {
                    mTimePickerDialog.show()
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "pick event time")
                Text(text = mTime.value.ifEmpty { stringResource(R.string.select_time) } )
            }
        }


        Row(horizontalArrangement = Arrangement.End){
            FilledTonalButton(
                enabled = viewModel.selectedDateIsValid(selectedDate.value, mTime.value) && eventName.value.isNotEmpty(),
                onClick = {
                    viewModel.saveEvent(
                        name = eventName.value,
                        description = eventDescription.value,
                        date = viewModel.getDate(selectedDate.value, mTime.value),
                        icon = selectedIcon.value?.name
                    )
                    scope.launch {
                        delay(400)
                        snackbarHostState.showSnackbar("Event '${eventName.value}' created")
                        onSaveEventClick()
                    }

                }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "save event")
                Text(text = stringResource(R.string.save))
            }
        }
    }
}



@OptIn(ExperimentalLayoutApi::class)
@Composable
fun IconSelectionBox(viewModel: AddEventViewModel, selectedIcon: MutableState<ImageVector?>){
    var expanded by remember { mutableStateOf(false) }
    val icons = viewModel.icons
    if(selectedIcon.value == null) selectedIcon.value = Icons.Default.DateRange


    FilledIconButton(
        onClick = { expanded = true },
        modifier = Modifier
            .height(48.dp)
            .width(48.dp)
    ){
        Icon(imageVector = selectedIcon.value!!, contentDescription = null)
    }
    if (expanded) {
        Dialog(
            onDismissRequest = { expanded = false }){
            Surface(modifier = Modifier
                .width(300.dp)
                .height(400.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column (
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.Top),
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    HeaderText(text = "Select icon")
                    Divider()
                    FlowRow(
                        modifier = Modifier
                            .wrapContentWidth(Alignment.CenterHorizontally, unbounded = false)
                            .verticalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        content = {
                        icons.forEach{ icon ->
                            FilledIconButton(
                                onClick = {
                                    selectedIcon.value = icon.value
                                    expanded = false
                                          },
                                modifier = Modifier
                                    .height(48.dp)
                                    .width(48.dp)
                            ) {
                                Icon(imageVector = icon.value, contentDescription = icon.key)
                            }
                        }
                    })
                }
            }
        }
    }
}




