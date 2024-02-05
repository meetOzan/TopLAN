package com.gdscedirne.toplan

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.gdscedirne.toplan.components.BottomNav
import com.gdscedirne.toplan.components.Screen
import com.gdscedirne.toplan.navigation.TopLanNavGraph
import com.gdscedirne.toplan.ui.theme.DarkRed
import com.gdscedirne.toplan.ui.theme.TopLANTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TopLANTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomAppBar(
                            modifier = Modifier
                                .height(65.dp)
                                .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
                            cutoutShape = CircleShape,
                            backgroundColor = Color.White,
                            elevation = 22.dp
                        ) {
                            BottomNav(navController = navController)
                        }
                    },
                    floatingActionButtonPosition = FabPosition.Center,
                    isFloatingActionButtonDocked = true,
                    floatingActionButton = {
                        FloatingActionButton(
                            shape = CircleShape,
                            onClick = {
                                Screen.Alert.route?.let {
                                    navController.navigate(it) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                                Screen.Alert.route?.let { navController.navigate(it) }
                            },
                            backgroundColor = DarkRed
                        ) {
                            Image(
                                painter = painterResource(id = Screen.Alert.icon!!),
                                contentDescription = getString(R.string.alert_icon)
                            )
                        }
                    }
                ) {
                    it
                    TopLanNavGraph(navController = navController)
                }
            }
        }
    }
}