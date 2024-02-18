package com.gdscedirne.toplan.presentation.home

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
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.IconButton
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.navigation.compose.rememberNavController
import com.gdscedirne.toplan.MainActivity
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.components.BottomNav
import com.gdscedirne.toplan.components.CustomAlertDialog
import com.gdscedirne.toplan.components.CustomDrawer
import com.gdscedirne.toplan.components.CustomLoading
import com.gdscedirne.toplan.components.CustomText
import com.gdscedirne.toplan.navigation.navgraph.TopLanNavGraph
import com.gdscedirne.toplan.presentation.home.viewmodel.HomeAction
import com.gdscedirne.toplan.presentation.home.viewmodel.HomeViewModel
import com.gdscedirne.toplan.presentation.report.ReportActivity
import com.gdscedirne.toplan.ui.theme.DarkRed
import com.gdscedirne.toplan.ui.theme.LightGrey20
import com.gdscedirne.toplan.ui.theme.MainRed
import com.gdscedirne.toplan.ui.theme.TopLANTheme
import com.gdscedirne.toplan.ui.theme.khandFamily
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TopLANTheme {

                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(
                    color = Color.White
                )
                systemUiController.setStatusBarColor(
                    color = Color.White
                )

                val navController = rememberNavController()
                val scope = rememberCoroutineScope()

                val scaffoldState = rememberScaffoldState()

                val permissionState = rememberPermissionState(
                    Manifest.permission.CALL_PHONE
                )

                val context = LocalContext.current

                val hasPermission = permissionState.status.isGranted

                val homeViewModel = hiltViewModel<HomeViewModel>()
                val homeUiState = homeViewModel.homeState.collectAsState().value

                LaunchedEffect(true) {
                    homeViewModel.onAction(HomeAction.GetUser)
                }

                if (homeUiState.sosCallDialog) {
                    CustomAlertDialog(
                        title = stringResource(R.string.sos_call),
                        body = stringResource(R.string.are_you_sure_you_want_to_make_an_emergency_call),
                        positiveButtonName = stringResource(R.string.yes),
                        negativeButtonName = stringResource(R.string.cancel),
                        onDismissClick = {
                            homeViewModel.onAction(
                                HomeAction.ChangeSosDialogState(
                                    false
                                )
                            )
                        },
                        onPositiveAction = {
                            makeEmergencyCall(context)
                        },
                        onNegativeAction = {
                            homeViewModel.onAction(
                                HomeAction.ChangeSosDialogState(
                                    false
                                )
                            )
                        }
                    )
                }

                if (homeUiState.isLoading){
                    CustomLoading()
                }

                Scaffold(
                    topBar = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    scope.launch {
                                        scaffoldState.drawerState.open()
                                    }
                                },
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
                                        homeViewModel.onAction(
                                            HomeAction.ChangeSosDialogState(
                                                true
                                            )
                                        )
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
                    drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
                    scaffoldState = scaffoldState,
                    drawerContent = {
                        CustomDrawer(
                            navController = navController,
                            closeDrawerAction = {
                                scope.launch {
                                    scaffoldState.drawerState.close()
                                }
                            },
                            onSignOut = {
                                homeViewModel.onAction(HomeAction.SignOut(
                                    onNavigate = {
                                        val intent = Intent(this@HomeActivity, MainActivity::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                        context.startActivity(intent)
                                    }
                                )
                                )
                            },
                            user = homeUiState.user,
                            context = this@HomeActivity
                        )
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
                                val intent = Intent(context, ReportActivity::class.java)
                                context.startActivity(intent)
                            },
                            backgroundColor = DarkRed
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.alert),
                                contentDescription = getString(R.string.alert_icon)
                            )
                        }
                    }
                ) {
                    TopLanNavGraph(navController = navController, modifier = Modifier.padding(it))
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