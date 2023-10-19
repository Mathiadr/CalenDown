package no.gruppe02.hiof.calendown.screen
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePicker(selectedDate: String, onDateChanged: (String) -> Unit) {
    val textState = remember { mutableStateOf(selectedDate) }

    TextField(
        value = textState.value,
        onValueChange = {
            textState.value = it
            onDateChanged(it)
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onDateChanged(textState.value)
            }
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
            .absoluteOffset(x = 0.dp, y = -20.dp)
    )
}