package no.gruppe02.hiof.calendown.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import no.gruppe02.hiof.calendown.ui.theme.BgColor
import no.gruppe02.hiof.calendown.ui.theme.Primary
import no.gruppe02.hiof.calendown.ui.theme.Secondary
import no.gruppe02.hiof.calendown.ui.theme.TextColor

@Composable
fun NormalText(textValue: String) {
   Text(
       text = textValue,
       modifier = Modifier
           .fillMaxWidth()
           .heightIn(min = 80.dp),
       style = TextStyle(
           fontSize = 24.sp,
           fontWeight = FontWeight.Normal,
           fontStyle = FontStyle.Normal
       ),
       color = TextColor,
       textAlign = TextAlign.Center
   )
}

@Composable
fun HeaderText(textValue: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        Text(
            text = textValue,
            modifier = Modifier
                .align(Alignment.Center),
            style = TextStyle(
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Normal
            ),
            color = TextColor,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputTextField(labelValue: String, imageVector: ImageVector) {

    val textValue = remember{
        mutableStateOf("")
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp)),
        label = {Text(text = labelValue)},
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Primary,
            focusedLabelColor = Primary,
            containerColor = BgColor
        ),

        keyboardOptions = KeyboardOptions.Default,
        value = textValue.value,
        onValueChange = { textValue.value = it },
        leadingIcon = {
            Icon(imageVector = imageVector, contentDescription = "")
        }

    )
}

@Composable
fun ButtonComponent (textValue: String) {
    Button(onClick = { /*TODO*/ },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(48.dp),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        shape = RoundedCornerShape(4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(48.dp)
                .background(
                    brush = Brush.horizontalGradient(listOf(Secondary, Primary)),

                ),
            contentAlignment = Alignment.Center
        )
        {
            Text(
                text = textValue,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ClickableTextComponent (
    initialText: String,
    actionText: String,
    onClick: () -> Unit = {}
    ) {
    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = Primary)) {
            pushStringAnnotation(tag = "action", annotation = actionText)
            append(actionText)
        }

    }

    ClickableText(
        style = TextStyle(
            fontSize = 19.sp,
            fontWeight = FontWeight.Normal,
            fontStyle = FontStyle.Normal
        ),
        text = annotatedString,
        onClick = { offset ->
            val position = annotatedString.getStringAnnotations("action", offset, offset)
            if (position.isNotEmpty()) {
                onClick.invoke()
            }
        }
    )
}