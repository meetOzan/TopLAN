package com.gdscedirne.toplan.components

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.data.model.User
import com.gdscedirne.toplan.navigation.destination.Destinations
import com.gdscedirne.toplan.ui.theme.Black
import com.gdscedirne.toplan.ui.theme.LightGrey5
import com.gdscedirne.toplan.ui.theme.MainRed
import com.gdscedirne.toplan.ui.theme.MainRed20
import com.gdscedirne.toplan.ui.theme.MediumGrey10
import com.gdscedirne.toplan.ui.theme.MediumGrey20
import com.gdscedirne.toplan.ui.theme.TransparentRed

@Composable
fun CustomDrawer(
    user: User = User(),
    navController: NavHostController,
    modifier: Modifier = Modifier,
    closeDrawerAction: () -> Unit,
    onSignOut: () -> Unit,
    context: Context
) {
    Surface(
        modifier = Modifier
            .wrapContentWidth()
            .then(modifier),
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
                        CustomText(
                            if (user.name == context.getString(R.string.empty)) context.getString(R.string.guest)
                            else user.name,
                            13,
                            color = Black
                        )
                        CustomText(
                            if (user.email == context.getString(R.string.empty)) context.getString(R.string.please_sign_in_to_continue)
                            else user.name,
                            13,
                            color = MediumGrey10
                        )
                    }
                }
                Divider(
                    modifier = Modifier
                        .height(2.dp)
                        .padding(top = 16.dp)
                        .background(LightGrey5)
                        .fillMaxWidth()
                )
                LazyColumn(
                    modifier = Modifier.padding(start = 8.dp, top = 48.dp),
                    verticalArrangement = Arrangement.spacedBy(36.dp),
                    content = {
                        items(4) {
                            ElevatedButton(
                                onClick = {
                                    when (it) {
                                        0 -> {
                                            if (user != User(
                                                    name = "",
                                                    email = ""
                                                )
                                            ) {
                                                navController.navigate(Destinations.ProfileDestination.route) {
                                                    popUpTo(Destinations.ProfileDestination.route) {
                                                        inclusive = true
                                                    }
                                                }
                                                closeDrawerAction()
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    context.getString(R.string.please_sign_in_to_continue),
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }

                                        1 -> {
                                            if (user != User(
                                                    name = context.getString(R.string.empty),
                                                    email = context.getString(R.string.empty)
                                                )
                                            ) {
                                                navController.navigate(Destinations.SettingsDestination.route) {
                                                    popUpTo(Destinations.SettingsDestination.route) {
                                                        inclusive = true
                                                    }
                                                }
                                                closeDrawerAction()
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    context.getString(R.string.please_sign_in_to_continue),
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }

                                        2 -> {
                                            if (user != User(
                                                    name = context.getString(R.string.empty),
                                                    email = context.getString(R.string.empty)
                                                )
                                            ) {
                                                navController.navigate(Destinations.ContactUsDestination.route) {
                                                    popUpTo(Destinations.ContactUsDestination.route) {
                                                        inclusive = true
                                                    }
                                                }
                                                closeDrawerAction()
                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    context.getString(R.string.please_sign_in_to_continue),
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        }

                                        3 -> {
                                            onSignOut()
                                        }
                                    }
                                },
                                elevation = ButtonDefaults.elevatedButtonElevation(
                                    defaultElevation = 0.dp
                                ),
                                colors = ButtonDefaults.elevatedButtonColors(
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
                                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
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
                        stringResource(R.string._2024_toplan),
                        14,
                        color = MediumGrey20,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                CustomText(
                    text = stringResource(R.string.all_rights_reserved),
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
    CustomDrawer(
        navController = NavHostController(
            context = androidx.compose.ui.platform.LocalContext.current
        ),
        closeDrawerAction = {},
        onSignOut = {},
        user = User(
            name = "John Doe",
            email = "johndoe@email.com"
        ),
        context = androidx.compose.ui.platform.LocalContext.current
    )
}
