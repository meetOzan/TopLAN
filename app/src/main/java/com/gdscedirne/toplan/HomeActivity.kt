package com.gdscedirne.toplan

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Scaffold
import androidx.compose.material3.IconButton
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import com.gdscedirne.toplan.components.BottomNav
import com.gdscedirne.toplan.components.CustomAlertDialog
import com.gdscedirne.toplan.components.CustomText
import com.gdscedirne.toplan.components.Screen
import com.gdscedirne.toplan.navigation.TopLanNavGraph
import com.gdscedirne.toplan.presentation.home.HomeAction
import com.gdscedirne.toplan.presentation.home.HomeViewModel
import com.gdscedirne.toplan.ui.theme.DarkRed
import com.gdscedirne.toplan.ui.theme.LightGrey20
import com.gdscedirne.toplan.ui.theme.MainRed
import com.gdscedirne.toplan.ui.theme.TopLANTheme
import com.gdscedirne.toplan.ui.theme.khandFamily
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalPermissionsApi::class)
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TopLANTheme {

                val navController = rememberNavController()

                val homeViewModel = hiltViewModel<HomeViewModel>()
                val homeUiState = homeViewModel.homeState.collectAsState().value

                val context = LocalContext.current

                val permissionState = rememberPermissionState(
                    Manifest.permission.CALL_PHONE
                )

                val hasPermission = permissionState.status.isGranted

                if (homeUiState.sosCallDialog) {
                    CustomAlertDialog(
                        title = stringResource(R.string.sos_call),
                        body = stringResource(R.string.are_you_sure_you_want_to_make_an_emergency_call),
                        positiveButtonName = stringResource(R.string.yes),
                        negativeButtonName = stringResource(R.string.cancel),
                        onDismissClick = { homeViewModel.onAction(HomeAction.ChangeSosDialogState(false)) },
                        onPositiveAction = {
                            makeEmergencyCall(context)
                        },
                        onNegativeAction = { homeViewModel.onAction(HomeAction.ChangeSosDialogState(false)) }
                    )
                }

                Scaffold(topBar = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = { },
                            modifier = Modifier.size(32.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.menu_image),
                                contentDescription = null,
                            )
                        }
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.toplan_icon),
                                contentDescription = null,
                                modifier = Modifier.size(32.dp),
                                colorFilter = ColorFilter.tint(MainRed)
                            )
                            CustomText(
                                text = stringResource(id = R.string.toplan),
                                fontSize = 32,
                                color = MainRed,
                                fontStyle = TextStyle(
                                    fontWeight = FontWeight.Medium,
                                    fontFamily = khandFamily
                                )
                            )
                        }
                        IconButton(
                            onClick = {
                                if (hasPermission) {
                                    homeViewModel.onAction(HomeAction.ChangeSosDialogState(true))
                                } else {
                                    permissionState.launchPermissionRequest()
                                }
                            },
                            modifier = Modifier
                                .size(30.dp, 30.dp)
                                .clip(
                                    RoundedCornerShape(0.dp)
                                )
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.sos_image),
                                contentDescription = null,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                },
                    bottomBar = {
                        BottomAppBar(
                            modifier = Modifier
                                .border(
                                    width = 1.dp,
                                    color = LightGrey20
                                )
                                .fillMaxWidth(),
                            cutoutShape = CircleShape,
                            backgroundColor = Color.White,
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

fun makeEmergencyCall(context: Context) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:911")
    context.startActivity(intent)
}