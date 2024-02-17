package com.gdscedirne.toplan.presentation.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.components.CustomElevatedButton
import com.gdscedirne.toplan.components.CustomText
import com.gdscedirne.toplan.data.model.User
import com.gdscedirne.toplan.ui.theme.Black
import com.gdscedirne.toplan.ui.theme.DarkGrey
import com.gdscedirne.toplan.ui.theme.LightGrey20
import com.gdscedirne.toplan.ui.theme.LightPink
import com.gdscedirne.toplan.ui.theme.Red
import com.gdscedirne.toplan.ui.theme.robatoFamily

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    user: User = User(name = "Guest"),
    profileOptionTitleList: List<ProfileOption>
) {

    Column(
        modifier = Modifier
            .background(LightGrey20)
            .fillMaxSize()
            .then(modifier),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp
            ),
            shape = RoundedCornerShape(0.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomText(
                    text = stringResource(R.string.settings),
                    color = Black,
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .align(Alignment.CenterHorizontally)
                )
                CustomText(
                    text = user.name[0].toString(),
                    color = Red,
                    fontSize = 34,
                    modifier = Modifier
                        .padding(vertical = 4.dp)
                        .drawWithContent {
                            drawCircle(color = LightPink, radius = 96f, center = center)
                            this.drawContent()
                        }
                )
                CustomText(
                    text = user.name, color = Black, fontSize = 16, fontStyle = TextStyle(
                        fontWeight = FontWeight.Medium,
                        fontFamily = robatoFamily
                    ),
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 36.dp),
            content = {
                items(3) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp, horizontal = 16.dp)
                    ) {
                        CustomText(
                            text = profileOptionTitleList[it].title,
                            color = Black,
                            fontSize = 14,
                            modifier = Modifier.padding()
                        )
                        CustomElevatedButton(
                            onClick = profileOptionTitleList[it].onNavigate,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(2.dp),
                            text = {
                                Row(
                                    Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    CustomText(
                                        text = profileOptionTitleList[it].option,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp),
                                        color = DarkGrey
                                    )
                                    Image(
                                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                        contentDescription = null
                                    )
                                }

                            })
                    }
                }
                item {
                    CustomElevatedButton(
                        onClick = {},
                        text = {
                            CustomText(
                                text = stringResource(R.string.user_policy),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        vertical = 8.dp
                                    ),
                                color = DarkGrey
                            )
                        },
                        shape = RoundedCornerShape(2.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewOfSettings() {
    SettingsScreen(
        user = User(
            name = "Guest"
        ),
        profileOptionTitleList = (
                listOf(
                    ProfileOption(
                        title = "Edit Profile",
                        option = "Edit",
                        onNavigate = {}
                    ),
                    ProfileOption(
                        title = "Change Password",
                        option = "Change",
                        onNavigate = {}
                    ),
                    ProfileOption(
                        title = "Log Out",
                        option = "Log Out",
                        onNavigate = {}
                    )

                )
                )
    )
}

data class ProfileOption(
    val title: String,
    val option: String,
    val onNavigate: () -> Unit
)