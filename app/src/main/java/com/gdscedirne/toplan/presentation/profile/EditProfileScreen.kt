package com.gdscedirne.toplan.presentation.profile

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.components.CoilImage
import com.gdscedirne.toplan.components.CustomElevatedButton
import com.gdscedirne.toplan.components.CustomText
import com.gdscedirne.toplan.components.CustomTextField
import com.gdscedirne.toplan.data.model.User
import com.gdscedirne.toplan.presentation.profile.viewmodel.ProfileAction
import com.gdscedirne.toplan.presentation.profile.viewmodel.ProfileUiState
import com.gdscedirne.toplan.ui.theme.Black
import com.gdscedirne.toplan.ui.theme.DarkGrey
import com.gdscedirne.toplan.ui.theme.LightPink
import com.gdscedirne.toplan.ui.theme.MainRed20
import com.gdscedirne.toplan.ui.theme.robatoFamily

@Composable
fun EditProfileScreen(
    modifier: Modifier = Modifier,
    profileUiState: ProfileUiState,
    onAction: (ProfileAction) -> Unit,
    list: List<EditProfileItem>,
    onHomeNavigate: () -> Unit
) {

    val context = LocalContext.current

    val pickImage = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedImageUri ->
            onAction(
                ProfileAction.UploadImageStorage(
                    uri = selectedImageUri,
                    context = context,
                    onSuccess = { imageUrl, _ ->
                        onAction(
                            ProfileAction.SetImageUri(
                                uri = selectedImageUri,
                                url = imageUrl
                            )
                        )
                    },
                    onFailure = {
                        Log.e(
                            context.getString(R.string.tag),
                            context.getString(R.string.oncreate, it)
                        )
                    },
                )
            )
        }
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            pickImage.launch(context.getString(R.string.image))
        }
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CustomText(
            text = stringResource(R.string.edit_profile),
            color = Black,
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )
        if (profileUiState.imageUrl != stringResource(id = R.string.empty)) {
            CoilImage(
                data = profileUiState.imageUrl,
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .size(72.dp)
                    .clip(CircleShape)
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.gallery_add),
                contentDescription = stringResource(R.string.profile_picture),
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .size(32.dp)
                    .drawWithContent {
                        drawCircle(color = LightPink, radius = 96f, center = center)
                        this.drawContent()
                    }
                    .clickable {
                        requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
            )
        }
        CustomText(
            text = stringResource(R.string.change_photo),
            color = MainRed20, fontSize = 14,
            fontStyle = TextStyle(
                fontWeight = FontWeight.Normal,
                fontFamily = robatoFamily
            ),
            modifier = Modifier
                .padding(top = 16.dp)
                .clickable {
                    requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }
        )
        LazyColumn(
            modifier = Modifier.padding(vertical = 16.dp),
            content = {
                items(4) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 8.dp)
                    ) {
                        CustomText(
                            text = list[it].title,
                            color = Black,
                            fontSize = 14,
                            modifier = Modifier.padding()
                        )
                        CustomTextField(
                            textTitle = list[it].text,
                            onValueChange = list[it].action,
                            placerHolder = {
                                CustomText(
                                    text = list[it].hint,
                                    color = DarkGrey
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp),
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
                        )
                    }
                }
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 8.dp)
                    ) {
                        CustomText(
                            text = stringResource(R.string.address),
                            color = Black,
                            fontSize = 14,
                            modifier = Modifier.padding()
                        )
                        CustomTextField(
                            textTitle = profileUiState.address,
                            onValueChange = { newAddress ->
                                onAction(ProfileAction.ChangeAddress(newAddress))
                            },
                            maxLines = 4,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                                .padding(top = 4.dp),
                            placerHolder = {
                                CustomText(
                                    text = stringResource(R.string.address),
                                    color = DarkGrey
                                )
                            },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                        )
                    }
                }
                item {
                    CustomElevatedButton(
                        onClick = {
                            if (profileUiState.name.isEmpty() || profileUiState.surname.isEmpty()
                                || profileUiState.phoneNumber.isEmpty() || profileUiState.relativeName.isEmpty()
                            ) {
                                Toast.makeText(
                                    context,
                                    context.getString(R.string.please_fill_all_fields),
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                onAction(
                                    ProfileAction.UpdateProfile(
                                        User(
                                            name = profileUiState.name,
                                            surname = profileUiState.surname,
                                            address = profileUiState.address,
                                            relativeName = profileUiState.relativeName,
                                            phone = profileUiState.phoneNumber,
                                            imageUrl =
                                            if (profileUiState.imageUrl == context.getString(R.string.empty))
                                                profileUiState.user.imageUrl
                                            else profileUiState.imageUrl,
                                            email = profileUiState.user.email,
                                            id = profileUiState.user.id,
                                            password = profileUiState.user.password,
                                        ),
                                        onHomeNavigate
                                    )
                                )
                            }
                        },
                        text = {
                            CustomText(
                                text = stringResource(R.string.save_the_changes),
                                color = Color.White,
                                fontSize = 16,
                                fontStyle = TextStyle(fontFamily = robatoFamily),
                                modifier = Modifier.padding(vertical = 6.dp)
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp, bottom = 24.dp),
                        color = ButtonDefaults.elevatedButtonColors(
                            containerColor = MainRed20
                        )
                    )
                }
            })
    }
}

data class EditProfileItem(
    val title: String,
    val text: String,
    val hint: String,
    val action: (String) -> Unit = {}
)

@Preview
@Composable
fun PreviewOfEditProfile() {
    EditProfileScreen(
        profileUiState = ProfileUiState(),
        onAction = {},
        list = listOf(
            EditProfileItem(
                title = "E-mail",
                text = "",
                hint = "Your e-mail address"
            ),
            EditProfileItem(
                title = "Phone",
                text = "",
                hint = "Your phone number"
            ),
            EditProfileItem(
                title = "Name",
                text = "",
                hint = "Your name"
            ),
            EditProfileItem(
                title = "Address",
                text = "",
                hint = "Your address"
            )
        ),
        onHomeNavigate = {}
    )
}