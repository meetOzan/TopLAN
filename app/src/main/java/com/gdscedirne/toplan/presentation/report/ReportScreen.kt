package com.gdscedirne.toplan.presentation.report

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.common.ReportOptions
import com.gdscedirne.toplan.components.CustomAlertDialog
import com.gdscedirne.toplan.components.CustomErrorDialog
import com.gdscedirne.toplan.components.CustomLoading
import com.gdscedirne.toplan.components.CustomText
import com.gdscedirne.toplan.data.model.Marker
import com.gdscedirne.toplan.ui.theme.Black
import com.gdscedirne.toplan.ui.theme.MainRed20
import com.gdscedirne.toplan.ui.theme.interFamily
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.UUID

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ReportScreen(
    reportUiState: ReportUiState,
    onReportAction: (ReportAction) -> Unit,
    reportList: List<ReportItem>,
    onHomeNavigate: () -> Unit,
    onReportNavigate: (String) -> Unit
) {

    val context = LocalContext.current

    // User location
    var userLatLng by remember {
        mutableStateOf(LatLng(0.0, 0.0))
    }

    // Location permission
    val locationPermissionState = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION,
    )

    // Camera position
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(1.35, 103.87), 10f)
    }

    // Request location permission
    LaunchedEffect(locationPermissionState) {
        if (!locationPermissionState.status.shouldShowRationale) {
            locationPermissionState.launchPermissionRequest()
        }
    }

    LaunchedEffect(locationPermissionState.status) {
        when (locationPermissionState.status) {
            PermissionStatus.Granted -> {
                val locationClient = LocationServices.getFusedLocationProviderClient(context)
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    locationClient.lastLocation
                        .addOnSuccessListener { location ->
                            if (location != null) {
                                onReportAction(
                                    ReportAction.ChangeLatLng(
                                        location.latitude,
                                        location.longitude
                                    )
                                )
                                userLatLng = LatLng(location.latitude, location.longitude)
                                cameraPositionState.position =
                                    CameraPosition.fromLatLngZoom(
                                        userLatLng,
                                        reportUiState.zoomLevel
                                    )

                            } else {
                                onReportAction(ReportAction.ChangeIsErrorDialog)
                            }
                        }.addOnFailureListener { exception ->
                            onReportAction(ReportAction.ChangeErrorMessage(exception.message.toString()))
                            onReportAction(ReportAction.ChangeIsErrorDialog)
                        }
                } else {
                    onReportAction(ReportAction.ChangeIsErrorDialog)
                }
            }

            is PermissionStatus.Denied -> {
                onReportAction(ReportAction.ChangeIsNoLocationRequestDialog)
            }
        }
    }


    if (reportUiState.isLoading) {
        CustomLoading()
    }

    Surface(
        modifier = Modifier,
        color = MainRed20
    ) {
        Column {
            LazyColumn(modifier = Modifier.padding(
                start = 24.dp,
                end = 24.dp,
                top = 86.dp
            ),
                content = {
                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),
                            elevation = 6.dp,
                            backgroundColor = Color.White,
                            shape = RoundedCornerShape(16.dp),
                        ) {
                            Column {
                                CustomText(
                                    text = stringResource(R.string.i_m_under_the_rubble),
                                    fontSize = 24,
                                    color = Black,
                                    modifier = Modifier
                                        .padding(top = 16.dp)
                                        .fillMaxWidth(),
                                    fontStyle = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = interFamily,
                                        textAlign = TextAlign.Center
                                    )
                                )
                                TextButton(
                                    onClick = {
                                        onReportAction(ReportAction.GetCurrentDate)
                                        onReportAction(ReportAction.GetCurrentTime)
                                        onReportAction(
                                            ReportAction.AddReportMarker(
                                                Marker(
                                                    id = UUID.randomUUID().toString(),
                                                    latitude = reportUiState.latitude,
                                                    longitude = reportUiState.longitude,
                                                    title = context.getString(R.string.demolish_building),
                                                    description = context.getString(R.string.i_m_under_the_rubble),
                                                    type = context.getString(R.string.demolition),
                                                    date = reportUiState.currentDate,
                                                    time = reportUiState.currentTime,
                                                )
                                            )
                                        )
                                    },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp, vertical = 24.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = MainRed20
                                    ),
                                    shape = RoundedCornerShape(16.dp)
                                ) {
                                    CustomText(
                                        text = stringResource(R.string.send_quick_location),
                                        fontSize = 16,
                                        color = Color.White,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp),
                                        fontStyle = TextStyle(
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = interFamily,
                                            textAlign = TextAlign.Center
                                        )
                                    )
                                }
                            }
                        }
                    }
                    items(4) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, bottom = if (it == 3) 8.dp else 16.dp)
                                .clickable {
                                    onReportNavigate(reportList[it].type.name)
                                },
                            elevation = 6.dp,
                            shape = RoundedCornerShape(16.dp),
                            backgroundColor = Color.White,
                        ) {
                            Column {
                                CustomText(
                                    text = reportList[it].title,
                                    fontSize = 24,
                                    color = Black,
                                    modifier = Modifier
                                        .padding(top = 16.dp)
                                        .fillMaxWidth(),
                                    fontStyle = TextStyle(
                                        fontWeight = FontWeight.Bold,
                                        fontFamily = interFamily,
                                        textAlign = TextAlign.Center
                                    )
                                )
                                CustomText(
                                    text = reportList[it].body,
                                    fontSize = 16,
                                    color = Black,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp, horizontal = 6.dp),
                                    fontStyle = TextStyle(
                                        fontFamily = interFamily,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                        }
                    }
                })
        }
    }

    if (reportUiState.isNoLocationRequestDialogOpen) {
        CustomAlertDialog(
            title = stringResource(R.string.no_location_permission),
            body = stringResource(R.string.you_need_to_give_location_permission_to_use_this_feature_please_give_location_permission_from_settings),
            positiveButtonName = stringResource(R.string.settings),
            negativeButtonName = stringResource(R.string.cancel),
            onDismissClick = { onReportAction(ReportAction.ChangeIsNoLocationRequestDialog) },
            onPositiveAction = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.fromParts(
                    context.getString(R.string.intent_package),
                    context.packageName,
                    null
                )
                ContextCompat.startActivity(context, intent, null)
                onReportAction(ReportAction.ChangeIsNoLocationRequestDialog)
            },
            onNegativeAction = {
                onHomeNavigate()
                onReportAction(ReportAction.ChangeIsNoLocationRequestDialog)
            }
        )
    }

    if (reportUiState.isErrorDialogOpen) {
        CustomErrorDialog(
            errorMessage = reportUiState.errorMessage,
            onDismissClick = {
                onReportAction(ReportAction.ChangeIsErrorDialog)
            },
            onPositiveAction = {
                onHomeNavigate()
                onReportAction(ReportAction.ChangeIsErrorDialog)
            }
        )
    }
}

data class ReportItem(
    val title: String,
    val body: String,
    val type: ReportOptions
)

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewOfReportScreen() {
    ReportScreen(
        reportUiState = ReportUiState(),
        onReportAction = {},
        reportList = listOf(
            ReportItem(
                title = stringResource(R.string.report_a_disaster),
                body = stringResource(R.string.there_has_been_or_is_about_to_be_a_natural_disaster_here),
                ReportOptions.DISASTER
            ),
            ReportItem(
                title = stringResource(R.string.supplies_and_equipment),
                body = stringResource(R.string.do_you_or_someone_in_your_neighbourhood_need_supplies_and_equipment),
                ReportOptions.SUPPLIES_EQUIPMENT
            ),
            ReportItem(
                title = stringResource(R.string.need_help),
                body = stringResource(R.string.do_you_want_to_report),
                ReportOptions.HELP
            ),
            ReportItem(
                title = stringResource(R.string.gathering_help),
                body = stringResource(R.string.share_assembly_areas),
                ReportOptions.GATHERING_AID
            )
        ),
        onHomeNavigate = {},
        onReportNavigate = { _ -> }
    )
}