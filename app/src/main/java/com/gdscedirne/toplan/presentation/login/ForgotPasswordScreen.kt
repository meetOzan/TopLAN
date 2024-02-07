package com.gdscedirne.toplan.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.components.CustomElevatedButton
import com.gdscedirne.toplan.components.CustomText
import com.gdscedirne.toplan.components.CustomTextField
import com.gdscedirne.toplan.ui.theme.Black
import com.gdscedirne.toplan.ui.theme.DarkGrey
import com.gdscedirne.toplan.ui.theme.DarkRed
import com.gdscedirne.toplan.ui.theme.DarkRed20
import com.gdscedirne.toplan.ui.theme.khandFamily
import com.gdscedirne.toplan.ui.theme.robatoFamily

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ForgotPasswordScreen(
    onSignInNavigate: () -> Unit,
    loginUiState: LoginUiState,
    onAction: (LoginOnAction) -> Unit
) {

    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(Color.White)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                keyboardController?.hide()
            }
            .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.toplan_icon),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = DarkRed),
                    modifier = Modifier.size(48.dp)
                )
                CustomText(
                    text = stringResource(R.string.toplan),
                    fontSize = 40,
                    color = DarkRed,
                    modifier = Modifier.padding(start = 4.dp),
                    fontStyle = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontFamily = khandFamily
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.padding(top = 24.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = {
                            onSignInNavigate()
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Image(
                            contentDescription = null,
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            colorFilter = ColorFilter.tint(Black),
                        )
                    }
                    CustomText(
                        text = stringResource(R.string.forgot_password),
                        fontSize = 24,
                        color = Black,
                        modifier = Modifier.padding(start = 16.dp),
                        fontStyle = TextStyle(
                            fontWeight = FontWeight.Medium,
                            fontFamily = robatoFamily
                        )
                    )
                }
            }
            CustomText(
                text = stringResource(R.string.you_can_change_your_password),
                modifier = Modifier.padding(top = 16.dp),
                color = DarkGrey,
                fontStyle = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontFamily = robatoFamily
                )
            )
            CustomTextField(
                textTitle = loginUiState.signInEmail,
                onValueChange = { newEmail ->
                    onAction(LoginOnAction.SignInEmailChanged(newEmail))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 36.dp),
                placerHolder = {
                    CustomText(
                        text = stringResource(R.string.e_mail_address),
                        color = DarkGrey
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
            )
            CustomElevatedButton(
                onClick = { },
                modifier = Modifier.padding(top = 16.dp),
                color = ButtonDefaults.elevatedButtonColors(
                    containerColor = DarkRed20
                ),
                text = {
                    CustomText(
                        text = stringResource(R.string.send),
                        color = Color.White,
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

@Preview
@Composable
fun PreviewForgotPassword() {
    ForgotPasswordScreen(
        onSignInNavigate = {},
        loginUiState = LoginUiState(),
        onAction = {}
    )
}