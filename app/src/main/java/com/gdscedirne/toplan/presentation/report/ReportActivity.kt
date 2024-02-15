package com.gdscedirne.toplan.presentation.report

import android.Manifest
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
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
import com.gdscedirne.toplan.navigation.ReportNavGraph
import com.gdscedirne.toplan.presentation.home.makeEmergencyCall
import com.gdscedirne.toplan.ui.theme.MainRed20
import com.gdscedirne.toplan.ui.theme.TopLANTheme
import com.gdscedirne.toplan.ui.theme.khandFamily
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
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
                                .padding(horizontal = 8.dp, vertical = 16.dp)
                                .background(MainRed20),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(modifier = Modifier.size(16.dp))
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                modifier = Modifier.padding(vertical = 16.dp)
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
                    it
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = Color.White
                    ) {
                        ReportNavGraph(navController = navController)
                    }
                }
            }
        }
    }
}