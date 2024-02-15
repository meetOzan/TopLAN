package com.gdscedirne.toplan.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.ui.theme.Black
import com.gdscedirne.toplan.ui.theme.LightGrey5
import com.gdscedirne.toplan.ui.theme.MainRed
import com.gdscedirne.toplan.ui.theme.MainRed20
import com.gdscedirne.toplan.ui.theme.MediumGrey10
import com.gdscedirne.toplan.ui.theme.MediumGrey20
import com.gdscedirne.toplan.ui.theme.TransparentRed

@Composable
fun CustomDrawer(
    onClickList: List<Unit>,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = Modifier.wrapContentWidth().then(modifier),
        color = Color.White
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.wrapContentWidth()
            ) {
                Row(
                    modifier = Modifier.padding(start = 20.dp, top = 56.dp, end = 24.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.toplan_icon),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(MainRed),
                        modifier = Modifier
                            .padding(vertical = 16.dp, horizontal = 8.dp)
                            .size(36.dp)
                            .drawWithContent {
                                drawCircle(
                                    color = TransparentRed,
                                    radius = 84f,
                                    center = center
                                )
                                this.drawContent()
                            },
                    )
                    Column(
                        modifier = Modifier.padding(start = 16.dp)
                    ) {
                        // TODO : Add user name
                        CustomText("user123", 13, color = Black)
                        // TODO : Add user email
                        CustomText("user@example.com", 13, color = MediumGrey10)
                    }
                }
                Divider(
                    modifier = Modifier
                        .height(1.dp)
                        .padding(top = 16.dp)
                        .background(LightGrey5)
                )
                LazyColumn(
                    modifier = Modifier.padding(start = 8.dp, top = 48.dp),
                    verticalArrangement = Arrangement.spacedBy(36.dp),
                    content = {
                        items(4) {
                            ElevatedButton(
                                onClick = {
                                    onClickList[it]
                                },
                                elevation = ButtonDefaults.elevatedButtonElevation(
                                    defaultElevation = 0.dp
                                ),
                                colors = androidx.compose.material3.ButtonDefaults.elevatedButtonColors(
                                    containerColor = Color.White
                                )
                            ) {
                                Image(
                                    painter = painterResource(id = drawerItems[it].icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(24.dp),
                                    colorFilter = ColorFilter.tint(MainRed20)
                                )
                                CustomText(
                                    drawerItems[it].title,
                                    16,
                                    color = Black,
                                    modifier = Modifier.padding(start = 16.dp, end  = 16.dp)
                                )
                            }
                        }
                    })
            }
            Column(
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Divider(
                    modifier = Modifier
                        .height(1.dp)
                        .background(LightGrey5)
                )
                Row(
                    modifier = Modifier.padding(bottom = 8.dp, top = 48.dp),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.copyright),
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                    )
                    CustomText(
                        "2024 - topLAN",
                        14,
                        color = MediumGrey20,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                CustomText(
                    text = "All rights reserved.",
                    14,
                    color = MediumGrey20,
                    modifier = Modifier.padding(bottom = 48.dp)
                )
            }
        }
    }
}

val drawerItems = listOf(
    ComponentItems(R.drawable.user_square, "Profile"),
    ComponentItems(R.drawable.red_settings, "Settings"),
    ComponentItems(R.drawable.feedback, "Contact Us"),
    ComponentItems(R.drawable.logout, "Logout")
)

data class ComponentItems(
    val icon: Int,
    val title: String
)

@Preview
@Composable
fun PreviewOfDrawer() {
    CustomDrawer(onClickList = listOf(Unit))
}
