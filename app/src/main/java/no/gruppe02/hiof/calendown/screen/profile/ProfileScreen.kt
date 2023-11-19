@file:OptIn(ExperimentalMaterial3Api::class)

package no.gruppe02.hiof.calendown.screen.profile

import android.annotation.SuppressLint
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import no.gruppe02.hiof.calendown.R
import no.gruppe02.hiof.calendown.components.ElevatedButtonComponent
import no.gruppe02.hiof.calendown.components.HeaderText


@SuppressLint("StateFlowValueCalledInComposition") // ?
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },

            )
        }
    ) { innerPadding ->

        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(MaterialTheme.colorScheme.background)) {
            Column (
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(18.dp, Alignment.Top),
                horizontalAlignment = Alignment.CenterHorizontally){

                PrimaryInfo(viewModel = viewModel)
                FriendListCard(viewModel = viewModel)
                //Text(text = "Logged in as " + viewModel.userId)
            }
        }
    }
}

@Composable
fun PrimaryInfo(viewModel: ProfileViewModel){
    val currentUser = viewModel.currentUser.value

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        tonalElevation = 4.dp,
        shadowElevation = 4.dp) {
        Column(
            modifier = Modifier.padding(6.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Profile Information",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.width(6.dp))
                Divider()
            }
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)){
                RoundImage(
                    image = painterResource(R.drawable.profilepic),
                    modifier = Modifier
                        .size(80.dp)
                )
                Text(
                    text = currentUser.username,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )

            }
            //ImgPicker(viewModel)
            ProfilePicturePicker(viewModel = viewModel)
        }
    }
}

@Composable
fun FriendListCard(viewModel: ProfileViewModel){
    val friends = viewModel.friendList.collectAsStateWithLifecycle().value
    viewModel.getFriendList()
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        tonalElevation = 4.dp,
        shadowElevation = 4.dp) {
        Column(
            modifier = Modifier.padding(6.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Friends",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.width(6.dp))
                Divider()
            }

            if (friends.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                        .padding(6.dp),
                    content = {
                    friends.forEach { friend ->
                        item {
                            ListItem(
                                headlineText = { Text(text = friend.username) },
                                leadingContent = {
                                    Icon(
                                        imageVector = Icons.Default.Face,
                                        contentDescription = null
                                    )
                                })
                        }
                    }
                })
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "You have no friends currently added... Try adding some!",
                        style = MaterialTheme.typography.labelMedium,
                        fontStyle = FontStyle.Italic,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Divider()
            Row(modifier = Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.End) {
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "Add friend")
                }
            }
        }

    }
}

@Composable
fun RoundImage (
    image: Painter,
    modifier: Modifier = Modifier
) {
    Image(
        painter = image,
        contentDescription = null,
        modifier = modifier
            .aspectRatio(1f, matchHeightConstraintsFirst = true)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface,
                shape = CircleShape
            )
            .padding(3.dp)
            .clip(CircleShape))
}

@Composable
fun ProfilePicturePicker(modifier: Modifier = Modifier, viewModel: ProfileViewModel){
    Button(modifier = modifier.fillMaxWidth(), onClick = { /*TODO*/ }) {
        Text(text = "Test")
    }
}

@Composable
fun ImgPicker(viewModel: ProfileViewModel) {

    var selectedImgUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImgUri = uri},
    )
    ElevatedButtonComponent(
        label = "Rediger profilbilde",
        onClick = {
            photoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        })
    if (selectedImgUri != null) {
        ElevatedButtonComponent(
            label = "Lagre",
            onClick = { viewModel.uploadProfileImg(selectedImgUri!!) })
    }
}

@Composable
fun ImgagePicker(onImageSelected: (Uri) -> Unit) {
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = {  uri: Uri? -> uri?.let { onImageSelected(it) }},
    )
    ElevatedButtonComponent(
        label = "Rediger profilbilde",
        onClick = {
            photoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        })
}

