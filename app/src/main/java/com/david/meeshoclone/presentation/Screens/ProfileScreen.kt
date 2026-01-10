package com.david.meeshoclone.presentation.Screens

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.david.meeshoclone.R
import com.david.meeshoclone.domain.models.UserData
import com.david.meeshoclone.domain.models.UserDataParent
import com.david.meeshoclone.presentation.Screens.utils.LogOutAlertDialog
import com.david.meeshoclone.presentation.ViewModels.ShoppingAppViewModel
import com.david.meeshoclone.presentation.navigation.SubNavigation
import com.google.firebase.auth.FirebaseAuth

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ShoppingAppViewModel = hiltViewModel(),
    firebaseAuth: FirebaseAuth,
    navController: NavController
) {
    LaunchedEffect(key1 = true) {
        viewModel.getUserById(firebaseAuth.currentUser!!.uid)
    }
    val profileScreenState = viewModel.profileScreenState.collectAsStateWithLifecycle()
    val updateScreenState = viewModel.updateScreenState.collectAsStateWithLifecycle()
    val userProfileImageState = viewModel.uploadUserProfileImageState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    val showDialog = remember { mutableStateOf(false) }
    val isEditing = remember { mutableStateOf(false) }
    val imageUri = rememberSaveable { mutableStateOf<Uri?>(null) }
    val imageUrl = remember { mutableStateOf("") }

    val firstname =
        remember { mutableStateOf(profileScreenState.value.UserData?.userData?.firstName ?: "") }
    val lastName =
        remember { mutableStateOf(profileScreenState.value.UserData?.userData?.lastName ?: "") }
    val email =
        remember { mutableStateOf(profileScreenState.value.UserData?.userData?.email ?: "") }
    val phoneNumber =
        remember { mutableStateOf(profileScreenState.value.UserData?.userData?.phoneNumber ?: "") }
    val address =
        remember { mutableStateOf(profileScreenState.value.UserData?.userData?.address ?: "") }


    LaunchedEffect(profileScreenState.value.UserData) {
        profileScreenState.value.UserData?.userData?.let { userData ->
            firstname.value = userData.firstName ?: ""
            lastName.value = userData.lastName ?: ""
            email.value = userData.email ?: ""
            phoneNumber.value = userData.phoneNumber ?: ""
            address.value = userData.address ?: ""
            imageUrl.value = userData.profileImage ?: ""
        }
    }

    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            viewModel.uploadUserProfileImage(uri)
            imageUri.value = uri
        }
    }

    if (updateScreenState.value.UserData != null) {
        Toast.makeText(context, updateScreenState.value.UserData, Toast.LENGTH_SHORT).show()
    } else if (updateScreenState.value.ErrorMsg != null) {
        Toast.makeText(context, updateScreenState.value.ErrorMsg, Toast.LENGTH_SHORT).show()
    } else if (updateScreenState.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    if (userProfileImageState.value.UserData != null) {
        imageUrl.value = userProfileImageState.value.UserData.toString()
    } else if (userProfileImageState.value.ErrorMsg != null) {
        Toast.makeText(context, userProfileImageState.value.ErrorMsg, Toast.LENGTH_SHORT).show()
    } else if (userProfileImageState.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }

    if (profileScreenState.value.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (profileScreenState.value.ErrorMsg != null) {
        Text(text = profileScreenState.value.ErrorMsg!!)

    } else if (profileScreenState.value.UserData != null) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "ACCOUNT",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navController.popBackStack()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "back"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {

                HorizontalDivider(
                    color = Color.LightGray,
                    thickness = 0.5.dp
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .align(Alignment.Start)
                    ) {
                        SubcomposeAsyncImage(
                            model = if (isEditing.value) imageUri.value else imageUrl.value,
                            contentDescription = "Profile Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(120.dp)
                                .clip(CircleShape)
                                .border(
                                    2.dp,
                                    color = colorResource(id = R.color.orange),
                                    CircleShape
                                )
                        ) {
                            when (painter.state) {
                                is AsyncImagePainter.State.Loading -> CircularProgressIndicator()
                                is AsyncImagePainter.State.Error -> Icon(
                                    Icons.Default.Person,
                                    contentDescription = "Profile Image",
                                )

                                else -> SubcomposeAsyncImageContent()
                            }
                        }
                        if (isEditing.value) {
                            IconButton(
                                onClick = {
                                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                                },
                                modifier = Modifier
                                    .size(40.dp)
                                    .align(Alignment.BottomEnd)
                                    .background(
                                        MaterialTheme.colorScheme.primary, CircleShape
                                    )
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = "Change Picture",
                                    tint = Color.White
                                )
                            }
                        }

                    }

                    Spacer(modifier = Modifier.size(16.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row {
                            OutlinedTextField(
                                value = firstname.value,
                                modifier = Modifier.weight(1f),
                                readOnly = if (isEditing.value) false else true,

                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = colorResource(id = R.color.orange),
                                    focusedBorderColor = colorResource(id = R.color.orange),
                                ),
                                shape = RoundedCornerShape(10.dp),

                                onValueChange = { firstname.value = it },
                                label = {
                                    Text("First Name")
                                }
                            )

                            Spacer(modifier = Modifier.size(16.dp))
                            OutlinedTextField(
                                value = lastName.value,
                                modifier = Modifier.weight(1f),
                                readOnly = if (isEditing.value) false else true,

                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = colorResource(id = R.color.orange),
                                    focusedBorderColor = colorResource(id = R.color.orange),
                                ),
                                shape = RoundedCornerShape(10.dp),

                                onValueChange = { lastName.value = it },
                                label = {
                                    Text("Last Name")
                                }
                            )
                        }


                        Spacer(modifier = Modifier.size(16.dp))
                        OutlinedTextField(
                            value = email.value,
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = if (isEditing.value) false else true,

                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = colorResource(id = R.color.orange),
                                focusedBorderColor = colorResource(id = R.color.orange),
                            ),
                            shape = RoundedCornerShape(10.dp),

                            onValueChange = { email.value = it },
                            label = {
                                Text("Email")
                            }
                        )

                        Spacer(modifier = Modifier.size(16.dp))
                        OutlinedTextField(
                            value = phoneNumber.value,
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = if (isEditing.value) false else true,

                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = colorResource(id = R.color.orange),
                                focusedBorderColor = colorResource(id = R.color.orange),
                            ),
                            shape = RoundedCornerShape(10.dp),

                            onValueChange = { phoneNumber.value = it },
                            label = {
                                Text("Phone Number")
                            }
                        )

                        Spacer(modifier = Modifier.size(16.dp))
                        OutlinedTextField(
                            value = address.value,
                            modifier = Modifier.fillMaxWidth(),
                            readOnly = if (isEditing.value) false else true,

                            colors = OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = colorResource(id = R.color.orange),
                                focusedBorderColor = colorResource(id = R.color.orange),
                            ),
                            shape = RoundedCornerShape(10.dp),

                            onValueChange = { address.value = it },
                            label = {
                                Text("Address")
                            }
                        )

                        if (showDialog.value) {
                            LogOutAlertDialog(
                                onDismiss = {
                                    showDialog.value = false
                                },
                                onConfirm = {
                                    firebaseAuth.signOut()
                                    navController.navigate(SubNavigation.LoginSignUpScreen)
                                }
                            )
                        }

                        Spacer(modifier = Modifier.size(16.dp))
                        if (isEditing.value == false) {
                            Button(
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                                shape = RoundedCornerShape(8.dp),
                                border = BorderStroke(1.dp, colorResource(id = R.color.orange)),
                                onClick = {
                                    isEditing.value = !isEditing.value
                                }
                            ) {
                                Text(
                                    "Edit Profile",
                                    color = colorResource(id = R.color.orange),
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        } else {
                            OutlinedButton(
                                onClick = {
                                    val updateUserData = UserData(
                                        firstName = firstname.value,
                                        lastName = lastName.value,
                                        email = email.value,
                                        phoneNumber = phoneNumber.value,
                                        address = address.value,
                                        profileImage = imageUrl.value
                                    )
                                    val userDataParent = UserDataParent(
                                        nodeId = profileScreenState.value.UserData!!.nodeId,
                                        userData = updateUserData
                                    )
                                    viewModel.updateUserData(
                                        userDataParent = userDataParent
                                    )
                                    isEditing.value = !isEditing.value
                                },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(Color.Blue)
                            ) {
                                Text("Save Profile")
                            }
                        }

                        Spacer(modifier = Modifier.size(16.dp))
                        OutlinedButton(
                            onClick = {
                                showDialog.value = true
                            },
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.orange))
                        ) {
                            Text(
                                "Log Out", color = Color.White, fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }


        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ProfileScreenPreview() {
//    ProfileScreen()
//}