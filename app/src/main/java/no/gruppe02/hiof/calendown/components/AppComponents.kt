package no.gruppe02.hiof.calendown.components

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import no.gruppe02.hiof.calendown.R

@Composable
fun ProfileImage (
    imageUrl: Uri?,
    modifier: Modifier = Modifier
) {
    val fallbackImage = R.drawable.profilepic
    AsyncImage(
        model = imageUrl ?: fallbackImage,
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface,
                shape = CircleShape
            )
            .padding(3.dp)
            .clip(CircleShape)
    )
}
@Composable
fun HeaderText(
    text: String,
    modifier: Modifier = Modifier) {

        Text(
            text = text,
            style = MaterialTheme.typography.displaySmall,
            textAlign = TextAlign.Center,
            modifier = modifier
                .fillMaxWidth()
        )
}

@Composable
fun BasicContainer(modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit){
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 4.dp,
        shadowElevation = 4.dp) {
        Column(
            modifier = modifier.padding(6.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            content = content
        )
    }
}

// Cannot be immediately followed by a LazyColumn/LazyRow or any other component with infinite constraint
@Composable
fun BasicScreenLayout(innerPadding: PaddingValues, modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit){
    Surface(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .background(MaterialTheme.colorScheme.background)) {
        Column(
            modifier = modifier
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(18.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicTextField (
    value: String,
    onNewValue: (String) -> Unit,
    label: String,
    imageVector: ImageVector,
    modifier: Modifier = Modifier) {

    OutlinedTextField(
        singleLine = true,
        modifier = modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = { onNewValue(it) },
        label = { Text(text = label) },
        leadingIcon = { Icon(imageVector = imageVector, contentDescription = "Username") })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailField(
    value: String,
    onNewValue: (String) -> Unit,
    label: String,
    imageVector: ImageVector,
    modifier: Modifier = Modifier) {

    OutlinedTextField(
        singleLine = true,
        modifier = modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = { onNewValue(it) },
        label = { Text(label) },
        leadingIcon = { Icon(imageVector = imageVector, contentDescription = "Email") })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordField(
    value: String,
    onNewValue: (String) -> Unit,
    label: String,
    imageVector: ImageVector,
    modifier: Modifier = Modifier) {

    OutlinedTextField(
        singleLine = true,
        modifier = modifier
            .fillMaxWidth(),
        value = value,
        onValueChange = { onNewValue(it) },
        label = { Text(label) },
        leadingIcon = { Icon(imageVector = imageVector, contentDescription = "Email") })
}

@Composable
fun ElevatedButtonComponent (
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier) {

    ElevatedButton(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        colors = ButtonDefaults.elevatedButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )) {
        Text(
            text = label,
            fontSize = 16.sp)
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
        withStyle(style = SpanStyle(

            color = MaterialTheme.colorScheme.primary)) {

            pushStringAnnotation(tag = "action", annotation = actionText)
            append(actionText)
        }
    }

    ClickableText(
        style = TextStyle(
            fontSize = 19.sp,
            color = MaterialTheme.colorScheme.onBackground
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