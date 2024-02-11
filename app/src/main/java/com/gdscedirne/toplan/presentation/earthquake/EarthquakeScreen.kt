package com.gdscedirne.toplan.presentation.earthquake

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.components.CustomAlertDialog
import com.gdscedirne.toplan.components.CustomErrorDialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun EarthQuakeScreen(
    onHomeNavigate: () -> Unit,
    onAction: (EarthquakeAction) -> Unit,
    earthquakeUiState: EarthquakeUiState
) {

    // Context
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
                                onAction(
                                    EarthquakeAction.ChangeLatLng(
                                        location.latitude,
                                        location.longitude
                                    )
                                )
                                userLatLng = LatLng(location.latitude, location.longitude)
                                cameraPositionState.position =
                                    CameraPosition.fromLatLngZoom(
                                        userLatLng,
                                        earthquakeUiState.zoomLevel
                                    )

                            } else {
                                onAction(EarthquakeAction.ChangeIsErrorDialog)
                            }
                        }
                        .addOnFailureListener { exception ->
                            onAction(EarthquakeAction.ChangeErrorMessage(exception.message.toString()))
                            onAction(EarthquakeAction.ChangeIsErrorDialog)
                        }
                } else {
                    onAction(EarthquakeAction.ChangeIsErrorDialog)
                }

            }

            is PermissionStatus.Denied -> {
                onAction(EarthquakeAction.ChangeIsNoLocationRequestDialog)
            }
        }
    }

    Box(
        Modifier
            .fillMaxSize()
            .padding(bottom = 56.dp, end = 8.dp, start = 8.dp, top = 16.dp)
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
        ) {
            Marker(
                state = MarkerState(
                    position = userLatLng,
                ),
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE),
                title = "Current Location",
                snippet = "You are here",
                draggable = false
            )
        }
    }

    if (earthquakeUiState.isErrorDialog) {
        CustomErrorDialog(
            errorMessage = earthquakeUiState.errorMessage,
            onDismissClick = {
                onAction(EarthquakeAction.ChangeIsErrorDialog)
            },
            onPositiveAction = {
                onHomeNavigate()
            }
        )
    }

    if (earthquakeUiState.isNoLocationRequestDialog) {
        CustomAlertDialog(
            title = stringResource(R.string.no_location_permission),
            body = stringResource(R.string.you_need_to_give_location_permission_to_use_this_feature_please_give_location_permission_from_settings),
            positiveButtonName = stringResource(R.string.settings),
            negativeButtonName = stringResource(R.string.cancel),
            onDismissClick = { onAction(EarthquakeAction.ChangeIsNoLocationRequestDialog) },
            onPositiveAction = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.data = Uri.fromParts(
                    context.getString(R.string.intent_package),
                    context.packageName,
                    null
                )
                startActivity(context, intent, null)
            },
            onNegativeAction = { onHomeNavigate() }
        )
    }

}

@Preview
@Composable
fun PreviewOfEarthquakeScreen() {
    EarthQuakeScreen(
        onHomeNavigate = {},
        onAction = {},
        earthquakeUiState = EarthquakeUiState.initial()
    )
}