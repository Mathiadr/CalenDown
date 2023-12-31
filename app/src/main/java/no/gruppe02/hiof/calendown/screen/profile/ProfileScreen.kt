@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package no.gruppe02.hiof.calendown.screen.profile

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import no.gruppe02.hiof.calendown.R
import no.gruppe02.hiof.calendown.components.BasicScreenLayout
import no.gruppe02.hiof.calendown.components.ElevatedButtonComponent
import no.gruppe02.hiof.calendown.components.HeaderText
import no.gruppe02.hiof.calendown.components.ProfileImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.profile),
                        style = MaterialTheme.typography.displaySmall,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },

            )
        }
    ) { innerPadding ->

        BasicScreenLayout(
            innerPadding = innerPadding,
            modifier = Modifier.verticalScroll(rememberScrollState())
        ){
            PrimaryInfo(viewModel = viewModel)
            FriendListCard(viewModel = viewModel)
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
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 4.dp,
        shadowElevation = 4.dp) {
        Column(
            modifier = Modifier.padding(6.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ){
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.profile_information),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold)
                Spacer(modifier = Modifier.width(6.dp))
                Divider()
            }
            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)){
                ProfileImage(
                    imageUrl = viewModel.userImage.value,
                    modifier = Modifier.size(80.dp))
                Text(
                    text = currentUser.username,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )

            }
            ImgPicker(viewModel)
            //ProfilePicturePicker(viewModel = viewModel)
        }
    }
}

@Composable
fun FriendListCard(viewModel: ProfileViewModel){
    val friends = viewModel.friendList.collectAsStateWithLifecycle().value
    val friendImages = viewModel.friendImages.toMap()
    val openSendFriendRequestDialog = remember { mutableStateOf(false) }

    viewModel.getFriendList()
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        shape = RoundedCornerShape(16.dp),
        tonalElevation = 4.dp,
        shadowElevation = 4.dp) {
        Column(
            modifier = Modifier.padding(6.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(R.string.friends),
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
                                    ProfileImage(imageUrl = friendImages[friend.uid],
                                        modifier = Modifier.size(50.dp))
                                },
                                trailingContent = {
                                    FriendOptionDropdownMenu(viewModel = viewModel, friendId = friend.uid)
                                }
                            )
                        }
                    }
                })
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = stringResource(R.string.no_friends_detected),
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
                TextButton(onClick = { openSendFriendRequestDialog.value = true }) {
                    Text(text = stringResource(R.string.add_friend))
                }
            }
        }

    }
    if (openSendFriendRequestDialog.value) {
        SearchDialog(viewModel = viewModel, closeDialog = { openSendFriendRequestDialog.value = false })
    }
}

@Composable
fun FriendOptionDropdownMenu(viewModel: ProfileViewModel, friendId: String){
    val expandFriendOptions = remember { mutableStateOf(false) }
    val openDeleteFriendDialog = remember { mutableStateOf(false) }

    IconButton(onClick = { expandFriendOptions.value = true }) {
        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "More actions for friend")
        Box(modifier = Modifier.wrapContentSize()){
            DropdownMenu(
                expanded = expandFriendOptions.value,
                onDismissRequest = { expandFriendOptions.value = false }) {
                DropdownMenuItem(
                    text = {
                        Text(text = stringResource(R.string.delete_friend), color = Color.Red)
                    },
                    onClick = {
                        openDeleteFriendDialog.value = true
                    }
                )
            }
        }
    }

    if (openDeleteFriendDialog.value) {
        RemoveFriendDialog(
            viewModel = viewModel,
            friendId = friendId,
            closeDialog = { openDeleteFriendDialog.value = false })
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
        label = stringResource(R.string.edit_profile_picture),
        onClick = {
            photoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        })
    if (selectedImgUri != null) {
        ElevatedButtonComponent(
            label = stringResource(R.string.save),
            onClick = { viewModel.uploadProfileImg(selectedImgUri!!) })
    }
}

@Composable
fun SearchDialog(
    viewModel: ProfileViewModel,
    closeDialog: () -> Unit){

    var searchQuery by remember { mutableStateOf("") }
    val searchResult = viewModel.searchResults.collectAsState().value
    val searchResultImages = viewModel.searchResultProfileImages.toMap()
    val selectedUserId = remember { mutableStateOf("") }

    Dialog(
        onDismissRequest = { closeDialog() }){
        Surface(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                HeaderText(text = stringResource(R.string.search_for_user))
                TextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                        viewModel.searchUsers(it)
                    },
                    maxLines = 1,
                    shape = RoundedCornerShape(25.dp),
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }
                )
                Spacer(modifier = Modifier.height(5.dp))
                Divider()
                Box {
                    LazyColumn(
                        modifier = Modifier
                            .height(450.dp)
                            .fillMaxWidth(),
                        content = {
                            searchResult.forEach { user ->
                                item {
                                    ListItem (
                                        headlineText = {
                                            Text(text = user.username)
                                        },
                                        leadingContent = {
                                            ProfileImage(
                                                imageUrl = searchResultImages[user.uid],
                                                modifier = Modifier.size(50.dp)
                                                )
                                        },
                                        trailingContent = {
                                                RadioButton(
                                                    selected = user.uid == selectedUserId.value,
                                                    onClick = {
                                                        if (user.uid != selectedUserId.value) selectedUserId.value = user.uid
                                                        else selectedUserId.value = ""
                                                })
                                        })
                                }
                                viewModel.getFriendList()
                            }
                        }
                    )
                    Divider()
                }
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly) {
                    TextButton(
                        onClick = {
                            Log.d("ProfileScreen", "Sending friend request to $selectedUserId")
                            viewModel.sendFriendRequest(selectedUserId.value)
                            closeDialog()
                        },
                        enabled = selectedUserId.value.isNotEmpty()) {
                        // Originally supposed to send a friend request, but was not implemented in time
                        Text(text = stringResource(R.string.add_friend))
                    }
                    TextButton(onClick = { closeDialog() }) {
                        Text(text = stringResource(R.string.cancel))
                    }
                }
            }
        }
    }
}

@Composable
fun RemoveFriendDialog(viewModel: ProfileViewModel, friendId: String,  closeDialog: () -> Unit){
    AlertDialog(
        title = {
            Text(text = stringResource(R.string.remove_friend_dialog_unfriend), color = Color.Red)
        },
        text = {
            Text(text = stringResource(R.string.delete_friend_dialog_desc))
        },
        icon = {
            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
        },
        onDismissRequest = { closeDialog() },
        confirmButton = {
            TextButton(onClick = {
                closeDialog()
                viewModel.removeFriend(friendId)
            }) {
                Text(text = stringResource(R.string.delete), color = Color.Red)
            }
        },
        dismissButton = {
            TextButton(onClick = { closeDialog() }) {
                Text(text = stringResource(R.string.cancel))
            }
        }
    )
}

