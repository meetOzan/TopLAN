package com.gdscedirne.toplan.presentation.report

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.components.CustomAlertDialog
import com.gdscedirne.toplan.components.CustomText
import com.gdscedirne.toplan.navigation.navgraph.ReportNavGraph
import com.gdscedirne.toplan.presentation.home.HomeActivity
import com.gdscedirne.toplan.presentation.home.makeEmergencyCall
import com.gdscedirne.toplan.presentation.report.viewModel.ReportAction
import com.gdscedirne.toplan.presentation.report.viewModel.ReportViewModel
import com.gdscedirne.toplan.ui.theme.MainRed20
import com.gdscedirne.toplan.ui.theme.TopLANTheme
import com.gdscedirne.toplan.ui.theme.khandFamily
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ReportActivity : AppCompatActivity() {
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TopLANTheme {

                val navController = rememberNavController()

                val permissionState = rememberPermissionState(
                    Manifest.permission.CALL_PHONE
                )

                val systemUiController = rememberSystemUiController()
                systemUiController.setStatusBarColor(
                    color = Color.Red
                )
                systemUiController.setSystemBarsColor(
                    color = MainRed20
                )

                val hasPermission = permissionState.status.isGranted

                val reportViewModel = hiltViewModel<ReportViewModel>()
                val reportUiState = reportViewModel.reportState.collectAsState().value

                if (reportUiState.sosCallDialog) {
                    CustomAlertDialog(
                        title = stringResource(R.string.sos_call),
                        body = stringResource(R.string.are_you_sure_you_want_to_make_an_emergency_call),
                        positiveButtonName = stringResource(R.string.yes),
                        negativeButtonName = stringResource(R.string.cancel),
                        onDismissClick = {
                            reportViewModel.onAction(
                                ReportAction.ChangeSosDialogState(
                                    false
                                )
                            )
                        },
                        onPositiveAction = {
                            makeEmergencyCall(this)
                        },
                        onNegativeAction = {
                            reportViewModel.onAction(
                                ReportAction.ChangeSosDialogState(
                                    false
                                )
                            )
                        }
                    )
                }
                Scaffold(
                    modifier = Modifier.background(MainRed20),
                    topBar = {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MainRed20),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = {
                                    val intent =
                                        Intent(this@ReportActivity, HomeActivity::class.java)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                                    this@ReportActivity.startActivity(intent)
                                },
                                modifier = Modifier
                                    .padding(top = 8.dp, bottom = 8.dp, start = 8.dp)
                                    .size(24.dp)
                            ) {
                                Image(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(Color.White),
                                )
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.padding(vertical = 8.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.toplan_icon),
                                    contentDescription = null,
                                    modifier = Modifier.size(32.dp),
                                    colorFilter = ColorFilter.tint(Color.White)
                                )
                                CustomText(
                                    text = stringResource(id = R.string.toplan),
                                    fontSize = 32,
                                    color = Color.White,
                                    fontStyle = TextStyle(
                                        fontWeight = FontWeight.Medium,
                                        fontFamily = khandFamily
                                    )
                                )
                            }
                            IconButton(
                                onClick = {
                                    if (hasPermission) {
                                        reportViewModel.onAction(
                                            ReportAction.ChangeSosDialogState(
                                                true
                                            )
                                        )
                                    } else {
                                        permissionState.launchPermissionRequest()
                                    }
                                },
                                modifier = Modifier
                                    .padding(top = 8.dp, bottom = 8.dp, end = 8.dp)
                                    .size(30.dp, 30.dp)
                                    .clip(
                                        RoundedCornerShape(0.dp)
                                    )
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.sos_image),
                                    contentDescription = null,
                                    modifier = Modifier.padding(4.dp),
                                    colorFilter = ColorFilter.tint(Color.White)
                                )
                            }
                        }
                    },
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize().padding(it),
                        color = Color.White
                    ) {
                        ReportNavGraph(navController = navController)
                    }
                }
            }
        }
    }
}