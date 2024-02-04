package com.gdscedirne.toplan.presentation.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.components.CustomElevatedButton
import com.gdscedirne.toplan.components.CustomText
import com.gdscedirne.toplan.ui.theme.LightGrey
import com.gdscedirne.toplan.ui.theme.MainRed
import com.gdscedirne.toplan.ui.theme.robatoFamily

@Composable
fun WelcomeScreen() {
    Column(
        modifier = Modifier
            .padding(vertical = 48.dp, horizontal = 16.dp)
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.toplan_icon),
                contentDescription = null,
                modifier = Modifier.size(128.dp),
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(MainRed)
            )
            CustomText(
                text = stringResource(R.string.welcome),
                fontSize = 28,
                fontStyle = TextStyle(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(bottom = 6.dp)
            )
            CustomText(text = stringResource(R.string.it_s_great_to_have_you_with_us), fontSize = 16)
        }
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            CustomElevatedButton(
                onClick = { },
                modifier = Modifier,
                color = ButtonDefaults.elevatedButtonColors(
                    containerColor = MainRed
                ),
                text = {
                    CustomText(
                        text = stringResource(R.string.sign_up), color = Color.White,
                        modifier = Modifier.padding(vertical = 8.dp),
                        fontStyle = TextStyle(
                            fontFamily = robatoFamily
                        )
                    )
                },
            )
            CustomElevatedButton(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(),
                color = ButtonDefaults.elevatedButtonColors(
                    containerColor = LightGrey
                ),
                text = {
                    CustomText(
                        text = stringResource(R.string.sign_in), color = Color.Black,
                        modifier = Modifier.padding(vertical = 8.dp),
                        fontStyle = TextStyle(
                            fontFamily = robatoFamily
                        )
                    )
                },
            )
            CustomElevatedButton(
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(),
                border = BorderStroke(1.dp, MainRed),
                color = ButtonDefaults.elevatedButtonColors(
                    containerColor = Color.White
                ),
                text = {
                    CustomText(
                        text = stringResource(R.string.continue_as_a_guest), color = MainRed,
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

@Preview(showBackground = true)
@Composable
fun PrevWelcomeScreen() {
    WelcomeScreen()
}