package com.gdscedirne.toplan.presentation.contact_us

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.components.CustomText
import com.gdscedirne.toplan.components.CustomTextField
import com.gdscedirne.toplan.ui.theme.Black
import com.gdscedirne.toplan.ui.theme.DarkGrey
import com.gdscedirne.toplan.ui.theme.DarkWhite
import com.gdscedirne.toplan.ui.theme.MainRed20
import com.gdscedirne.toplan.ui.theme.MediumGrey

@Composable
fun ContactUsScreen(
    onHomeNavigation: () -> Unit
) {

    var contactUsText by remember {
        mutableStateOf("")
    }

    var contactUsEmail by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Spacer(modifier = Modifier.size(24.dp))
            CustomText(text = stringResource(R.string.contact_us), color = Black, fontSize = 16)
            Spacer(modifier = Modifier.size(24.dp))
        }
        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            CustomText(
                text = stringResource(R.string.your_message),
                fontSize = 14,
                color = MediumGrey,
            )
            CustomTextField(
                textTitle = contactUsText,
                onValueChange = {
                    contactUsText = it
                },
                maxLines = 15,
                placerHolder = {
                    CustomText(
                        text = stringResource(R.string.give_more_information),
                        color = DarkGrey
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .size(250.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(vertical = 16.dp)
        ) {
            CustomText(
                text = stringResource(R.string.e_mail_address),
                fontSize = 14,
                color = MediumGrey,
            )
            CustomTextField(
                textTitle = contactUsEmail,
                onValueChange = {
                    contactUsEmail = it
                },
                placerHolder = {
                    CustomText(
                        text = stringResource(R.string.your_e_mail_address),
                        color = DarkGrey
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )
        }

        ElevatedButton(
            onClick = {
                // TODO : Send the message
                onHomeNavigation()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 72.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MainRed20
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            CustomText(
                text = stringResource(R.string.send),
                color = DarkWhite,
                modifier = Modifier.padding(vertical = 8.dp),
            )
        }
    }

}

@Preview
@Composable
fun PreviewOfContactUs() {
    ContactUsScreen(onHomeNavigation = {})
}