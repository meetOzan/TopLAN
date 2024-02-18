package com.gdscedirne.toplan.presentation.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.components.CoilImage
import com.gdscedirne.toplan.components.CustomElevatedButton
import com.gdscedirne.toplan.components.CustomLoading
import com.gdscedirne.toplan.components.CustomText
import com.gdscedirne.toplan.components.CustomTextField
import com.gdscedirne.toplan.data.model.User
import com.gdscedirne.toplan.presentation.profile.viewmodel.ProfileUiState
import com.gdscedirne.toplan.ui.theme.Black
import com.gdscedirne.toplan.ui.theme.DarkGrey
import com.gdscedirne.toplan.ui.theme.LightPink
import com.gdscedirne.toplan.ui.theme.MainRed
import com.gdscedirne.toplan.ui.theme.MediumGrey
import com.gdscedirne.toplan.ui.theme.Red
import com.gdscedirne.toplan.ui.theme.robatoFamily

@Composable
fun ProfileScreen(
    user: User = User(name = "Guest"),
    profileUiState: ProfileUiState,
    modifier: Modifier = Modifier,
    onEditProfileNavigate: () -> Unit
) {

    when {
        profileUiState.isLoading -> {
            CustomLoading()
        }

        profileUiState.isError -> {
            CustomText(
                text = profileUiState.message,
                color = Color.Red,
                modifier = Modifier.padding(16.dp)
            )
        }

        else -> {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .then(modifier),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CustomText(
                    text = stringResource(R.string.my_profile),
                    color = Black,
                    modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
                )
                if (user.imageUrl != "") {
                    CoilImage(
                        data = user.imageUrl,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .size(84.dp)
                            .clip(CircleShape)
                    )
                } else {
                    CustomText(
                        text = user.name[0].toString(),
                        color = Red,
                        fontSize = 34,
                        modifier = Modifier
                            .padding(vertical = 24.dp)
                            .drawWithContent {
                                drawCircle(color = LightPink, radius = 96f, center = center)
                                this.drawContent()
                            }
                    )
                }
                CustomText(
                    text = user.name, color = Black, fontSize = 16, fontStyle = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontFamily = robatoFamily
                    )
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(top = 24.dp)
                ) {
                    CustomText(
                        text = stringResource(R.string.e_mail_address),
                        fontSize = 14,
                        color = MediumGrey,
                    )
                    CustomTextField(
                        textTitle = profileUiState.user.email,
                        readOnly = true,
                        placerHolder = {
                            CustomText(
                                text = stringResource(R.string.your_e_mail_address),
                                color = DarkGrey
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(top = 24.dp)
                ) {
                    CustomText(
                        text = stringResource(R.string.phone_number),
                        fontSize = 14,
                        color = MediumGrey,
                    )
                    CustomTextField(
                        textTitle = profileUiState.user.phone,
                        readOnly = true,
                        placerHolder = {
                            CustomText(
                                text = stringResource(R.string.your_phone_number),
                                color = DarkGrey
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(top = 24.dp)
                ) {
                    CustomText(
                        text = stringResource(R.string.full_name),
                        fontSize = 14,
                        color = MediumGrey,
                    )
                    CustomTextField(
                        textTitle = profileUiState.user.name + " " + user.surname,
                        readOnly = true,
                        placerHolder = {
                            CustomText(
                                text = stringResource(R.string.your_full_name),
                                color = DarkGrey
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                    )
                }

                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(top = 24.dp)
                ) {
                    CustomText(
                        text = stringResource(R.string.address),
                        fontSize = 14,
                        color = MediumGrey,
                    )
                    CustomTextField(
                        textTitle = user.address,
                        readOnly = true,
                        maxLines = 4,
                        placerHolder = {
                            CustomText(
                                text = stringResource(R.string.your_address),
                                color = DarkGrey
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(100.dp),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                    )
                }

                CustomElevatedButton(
                    onClick = {
                        onEditProfileNavigate()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, bottom = 56.dp),
                    border = BorderStroke(1.dp, MainRed),
                    color = ButtonDefaults.elevatedButtonColors(
                        containerColor = Color.White
                    ),
                    text = {
                        CustomText(
                            text = stringResource(R.string.edit_profile),
                            color = MainRed,
                            modifier = Modifier.padding(vertical = 8.dp),
                            fontStyle = TextStyle(
                                fontFamily = robatoFamily
                            )
                        )
                    },
                )

            }
        }
    }

}

@Preview
@Composable
fun PreviewOfProfileScreen() {
    ProfileScreen(
        user = User(
            id = "",
            name = "John",
            surname = "Doe",
            relativeName = "Jane Doe",
            address = "1234 Main St",
            phone = "123-456-7890",
            email = "dsamkpdas@email.com",
            password = "mjkjjj",
        ),
        profileUiState = ProfileUiState(),
        onEditProfileNavigate = {}
    )
}