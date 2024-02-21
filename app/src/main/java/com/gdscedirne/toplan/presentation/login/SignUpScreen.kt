package com.gdscedirne.toplan.presentation.login

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.components.CustomElevatedButton
import com.gdscedirne.toplan.components.CustomErrorDialog
import com.gdscedirne.toplan.components.CustomLoading
import com.gdscedirne.toplan.components.CustomText
import com.gdscedirne.toplan.components.CustomTextField
import com.gdscedirne.toplan.data.model.User
import com.gdscedirne.toplan.presentation.login.viewmodel.LoginOnAction
import com.gdscedirne.toplan.presentation.login.viewmodel.LoginUiState
import com.gdscedirne.toplan.ui.theme.Black
import com.gdscedirne.toplan.ui.theme.DarkGrey
import com.gdscedirne.toplan.ui.theme.DarkRed
import com.gdscedirne.toplan.ui.theme.DarkRed20
import com.gdscedirne.toplan.ui.theme.LightGrey30
import com.gdscedirne.toplan.ui.theme.MainRed
import com.gdscedirne.toplan.ui.theme.MediumGrey
import com.gdscedirne.toplan.ui.theme.khandFamily
import com.gdscedirne.toplan.ui.theme.robatoFamily
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SignUpScreen(
    onWelcomeNavigate: () -> Unit,
    onHomeNavigate: () -> Unit,
    onSignInNavigate: () -> Unit,
    loginUiState: LoginUiState,
    onAction: (LoginOnAction) -> Unit = {}
) {

    var isPasswordVisible by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current

    val currentPage = rememberPagerState(
        pageCount = { 3 }
    )

    val coroutineScope = rememberCoroutineScope()

    if (loginUiState.isLoading) {
        CustomLoading()
    }

    if (loginUiState.isError) {
        CustomErrorDialog(
            errorMessage = loginUiState.errorMessage,
            onDismissClick = {
                onAction(LoginOnAction.ChangeErrorState(errorState = false, isLoading = false))
            },
            onPositiveAction = {
                onAction(LoginOnAction.ChangeErrorState(errorState = false, isLoading = false))
            }
        )
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                keyboardController?.hide()
            }
            .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            verticalArrangement = Arrangement.Center
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
                            onWelcomeNavigate()
                        },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Image(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Black),
                        )
                    }
                    CustomText(
                        text = stringResource(R.string.sign_up),
                        fontSize = 24,
                        color = Black,
                        modifier = Modifier.padding(start = 16.dp),
                        fontStyle = TextStyle(
                            fontWeight = FontWeight.Medium,
                            fontFamily = robatoFamily
                        )
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    CustomText(
                        text = "",
                        fontSize = 16,
                        color = Black,
                        modifier = Modifier.padding(top = 16.dp),
                        fontStyle = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontFamily = robatoFamily
                        )
                    )
                    CustomText(
                        text = "",
                        fontSize = 16,
                        color = Black,
                        modifier = Modifier.padding(top = 16.dp),
                        fontStyle = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontFamily = robatoFamily
                        )
                    )
                    CustomText(
                        text = "",
                        fontSize = 16,
                        color = Black,
                        modifier = Modifier.padding(top = 16.dp),
                        fontStyle = TextStyle(
                            fontWeight = FontWeight.Normal,
                            fontFamily = robatoFamily
                        )
                    )
                }
            }
            CustomText(
                text = stringResource(R.string.you_can_create_your_account),
                modifier = Modifier.padding(top = 16.dp),
                color = DarkGrey,
                fontStyle = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontFamily = robatoFamily
                )
            )
            HorizontalPager(
                state = currentPage,
                modifier = Modifier
                    .padding(vertical = 16.dp),
                pageSpacing = 16.dp
                ) { page ->
                when (page) {
                    0 -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Top
                        ) {
                            CustomTextField(
                                textTitle = loginUiState.number,
                                onValueChange = { newNumber ->
                                    onAction(LoginOnAction.NumberChanged(newNumber))
                                },
                                prefix = {
                                    CustomText(
                                        text = stringResource(R.string._90),
                                        color = Black,
                                        modifier = Modifier.padding(end = 8.dp),
                                        fontStyle = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = robatoFamily
                                        )
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp),
                                placerHolder = {
                                    CustomText(
                                        text = stringResource(R.string.phone_number),
                                        color = DarkGrey
                                    )
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Phone)
                            )
                            CustomTextField(
                                textTitle = loginUiState.signUpEmail,
                                onValueChange = { newEmail ->
                                    onAction(LoginOnAction.SignUpEmailChanged(newEmail))
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp),
                                placerHolder = {
                                    CustomText(
                                        text = stringResource(R.string.e_mail_address),
                                        color = DarkGrey
                                    )
                                },
                                maxLines = 4,
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
                            )
                            CustomTextField(
                                textTitle = loginUiState.signUpPassword,
                                onValueChange = { newPassword ->
                                    onAction(LoginOnAction.SignUpPasswordChanged(newPassword))
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp),
                                placerHolder = {
                                    CustomText(
                                        text = stringResource(R.string.password),
                                        color = DarkGrey
                                    )
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                                trailingIcon = {
                                    IconButton(
                                        onClick = {
                                            isPasswordVisible = !isPasswordVisible
                                        }
                                    ) {
                                        Icon(
                                            painter = if (isPasswordVisible)
                                                painterResource(id = R.drawable.opened_eye)
                                            else painterResource(
                                                R.drawable.closed_eye
                                            ),
                                            modifier = Modifier.size(24.dp),
                                            contentDescription = null,
                                            tint = DarkRed20
                                        )
                                    }
                                },
                                visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            )
                            CustomElevatedButton(
                                onClick = {
                                    if (loginUiState.number.isEmpty() || loginUiState.signUpEmail.isEmpty() || loginUiState.signUpPassword.isEmpty()) {
                                        onAction(
                                            LoginOnAction.ChangeErrorStateWithMessage(
                                                errorState = true,
                                                isLoading = false,
                                                message = "Please fill all the fields and try again"
                                            )
                                        )
                                    } else {
                                        coroutineScope.launch {
                                            currentPage.animateScrollToPage(1)
                                            onAction(LoginOnAction.PagerChanged(2))
                                        }
                                    }
                                },
                                modifier = Modifier
                                    .padding(top = 36.dp)
                                    .fillMaxWidth(),
                                color = ButtonDefaults.elevatedButtonColors(
                                    containerColor = DarkRed20
                                ),
                                text = {
                                    CustomText(
                                        text = stringResource(R.string.next),
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

                    1 -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            CustomTextField(
                                textTitle = loginUiState.signUpName,
                                onValueChange = { newName ->
                                    onAction(LoginOnAction.NameChanged(newName))
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp),
                                placerHolder = {
                                    CustomText(
                                        text = stringResource(R.string.name),
                                        color = DarkGrey
                                    )
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                            )
                            CustomTextField(
                                textTitle = loginUiState.signUpSurname,
                                onValueChange = { newSurname ->
                                    onAction(LoginOnAction.SurnameChanged(newSurname))
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp),
                                placerHolder = {
                                    CustomText(
                                        text = stringResource(R.string.surname),
                                        color = DarkGrey
                                    )
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                            )
                            CustomTextField(
                                textTitle = loginUiState.signUpRelativeName,
                                onValueChange = { newRelativeName ->
                                    onAction(LoginOnAction.RelativeNameChanged(newRelativeName))
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 24.dp),
                                placerHolder = {
                                    CustomText(
                                        text = stringResource(R.string.one_relative_s_full_name),
                                        color = DarkGrey
                                    )
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 36.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CustomElevatedButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            currentPage.animateScrollToPage(0)
                                            onAction(LoginOnAction.PagerChanged(1))
                                        }
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 8.dp),
                                    color = ButtonDefaults.elevatedButtonColors(
                                        containerColor = LightGrey30
                                    ),
                                    text = {
                                        CustomText(
                                            text = stringResource(R.string.back),
                                            color = Color.White,
                                            modifier = Modifier.padding(vertical = 8.dp),
                                            fontStyle = TextStyle(
                                                fontFamily = robatoFamily
                                            )
                                        )
                                    },
                                )
                                CustomElevatedButton(
                                    onClick = {
                                        if (loginUiState.signUpName.isEmpty() || loginUiState.signUpSurname.isEmpty() || loginUiState.signUpRelativeName.isEmpty()) {
                                            onAction(
                                                LoginOnAction.ChangeErrorStateWithMessage(
                                                    errorState = true,
                                                    isLoading = false,
                                                    message = "Please fill all the fields"
                                                )
                                            )
                                        } else {
                                            coroutineScope.launch {
                                                currentPage.animateScrollToPage(2)
                                                onAction(LoginOnAction.PagerChanged(3))
                                            }
                                        }
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 8.dp),
                                    color = ButtonDefaults.elevatedButtonColors(
                                        containerColor = DarkRed20
                                    ),
                                    text = {
                                        CustomText(
                                            text = stringResource(R.string.next),
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

                    2 -> {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            CustomTextField(
                                textTitle = loginUiState.signUpAddress,
                                onValueChange = { newRelativeName ->
                                    onAction(LoginOnAction.AddressChanged(newRelativeName))
                                },
                                maxLines = 4,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(220.dp)
                                    .padding(top = 26.dp),
                                placerHolder = {
                                    CustomText(
                                        text = stringResource(R.string.address),
                                        color = DarkGrey
                                    )
                                },
                                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
                            )
                            CustomText(
                                text = stringResource(R.string.optional),
                                color = MediumGrey,
                                modifier = Modifier
                                    .padding(top = 4.dp, end = 4.dp)
                                    .fillMaxWidth(),
                                fontSize = 12,
                                fontStyle = TextStyle(
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = robatoFamily,
                                    textAlign = TextAlign.End
                                )
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 36.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CustomElevatedButton(
                                    onClick = {
                                        coroutineScope.launch {
                                            currentPage.animateScrollToPage(1)
                                            onAction(LoginOnAction.PagerChanged(2))
                                        }
                                    },
                                    modifier = Modifier
                                        .weight(0.6f)
                                        .padding(horizontal = 8.dp),
                                    color = ButtonDefaults.elevatedButtonColors(
                                        containerColor = LightGrey30
                                    ),
                                    text = {
                                        CustomText(
                                            text = stringResource(R.string.back),
                                            color = Color.White,
                                            modifier = Modifier.padding(vertical = 8.dp),
                                            fontStyle = TextStyle(
                                                fontFamily = robatoFamily
                                            )
                                        )
                                    },
                                )
                                CustomElevatedButton(
                                    onClick = {
                                        onAction(
                                            LoginOnAction.SignUpUser(
                                                User(
                                                    name = loginUiState.signUpName,
                                                    surname = loginUiState.signUpSurname,
                                                    relativeName = loginUiState.signUpRelativeName,
                                                    address = loginUiState.signUpAddress,
                                                    email = loginUiState.signUpEmail,
                                                    password = loginUiState.signUpPassword,
                                                    phone = loginUiState.number
                                                ),
                                                onHomeNavigate
                                            )
                                        )
                                        onAction(LoginOnAction.ChangeLoadingState(true))
                                    },
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 8.dp),
                                    color = ButtonDefaults.elevatedButtonColors(
                                        containerColor = DarkRed20
                                    ),
                                    text = {
                                        CustomText(
                                            text = stringResource(R.string.create_account),
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
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 56.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            CustomText(
                text = stringResource(R.string.already_have_an_account),
                color = DarkGrey,
                modifier = Modifier.padding(top = 16.dp),
                fontStyle = TextStyle(
                    fontWeight = FontWeight.Normal,
                    fontFamily = robatoFamily
                )
            )
            CustomText(
                text = stringResource(id = R.string.sign_in),
                color = MainRed,
                modifier = Modifier
                    .padding(top = 16.dp, start = 8.dp)
                    .clickable {
                        onSignInNavigate()
                    },
                fontStyle = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontFamily = robatoFamily
                )
            )
        }
    }
}

@Preview
@Composable
fun PreviewSignUp() {
    SignUpScreen(
        onWelcomeNavigate = {},
        onHomeNavigate = {},
        onSignInNavigate = {},
        loginUiState = LoginUiState()
    )
}