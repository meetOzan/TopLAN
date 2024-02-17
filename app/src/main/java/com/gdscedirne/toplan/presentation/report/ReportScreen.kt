package com.gdscedirne.toplan.presentation.report

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.gdscedirne.toplan.R
import com.gdscedirne.toplan.components.CoilImage
import com.gdscedirne.toplan.components.CustomLoading
import com.gdscedirne.toplan.components.CustomText
import com.gdscedirne.toplan.components.CustomTextField
import com.gdscedirne.toplan.data.model.Marker
import com.gdscedirne.toplan.presentation.report.viewModel.ReportAction
import com.gdscedirne.toplan.presentation.report.viewModel.ReportUiState
import com.gdscedirne.toplan.ui.theme.DarkGrey
import com.gdscedirne.toplan.ui.theme.DarkWhite
import com.gdscedirne.toplan.ui.theme.MainRed20
import com.gdscedirne.toplan.ui.theme.MediumGrey
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import java.util.UUID

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ReportOptionsScreen(
    onHomeNavigate: () -> Unit,
    reportUiState: ReportUiState,
    onAction: (ReportAction) -> Unit,
    dropDownList: List<String>
) {

    var expanded by remember { mutableStateOf(false) }

    val context = LocalContext.current

    val pickImage = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { selectedImageUri ->
            onAction(
                ReportAction.UploadImageStorage(
                    uri = selectedImageUri,
                    context = context,
                    onSuccess = { imageUrl, _ ->
                        onAction(
                            ReportAction.SetImageUri(
                                uri = selectedImageUri,
                                url = imageUrl
                            )
                        )
                    },
                    onFailure = {
                        Log.e("TAG", "onCreate: $it")
                    },
                )
            )
        }
    }

    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            pickImage.launch(context.getString(R.string.image))
        }
    }


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
                                onAction(ReportAction.ChangeIsErrorDialog)
                            }
                        }.addOnFailureListener { exception ->
                            onAction(ReportAction.ChangeErrorMessage(exception.message.toString()))
                            onAction(ReportAction.ChangeIsErrorDialog)
                        }
                } else {
                    onAction(ReportAction.ChangeIsErrorDialog)
                }
            }

            is PermissionStatus.Denied -> {
                onAction(ReportAction.ChangeIsNoLocationRequestDialog)
            }
        }
    }

    if (reportUiState.isLoading) {
        CustomLoading()
    }

    Column(
        modifier = Modifier
            .verticalScroll(
                rememberScrollState()
            )
            .padding(start = 16.dp, end = 16.dp, top = 24.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        CustomText(
            text = stringResource(R.string.enter_the_information_of_the_advert_you_want_to_publish),
            fontSize = 14,
            color = MediumGrey,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            CustomText(
                text = stringResource(R.string.type_of_report),
                fontSize = 14,
                color = MediumGrey,
            )
            CustomTextField(
                textTitle = reportUiState.typeOfReportText,
                onValueChange = {
                    onAction(
                        ReportAction.ChangeTypeOfReport(it)
                    )
                },
                readOnly = dropDownList.isNotEmpty(),
                placerHolder = {
                    CustomText(
                        text = stringResource(R.string.type),
                        color = DarkGrey
                    )
                },
                suffix = {
                    if (dropDownList.isEmpty()) return@CustomTextField
                    Image(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = null,
                        Modifier
                            .size(24.dp)
                            .clickable {
                                expanded = !expanded
                            },
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                },
                modifier = Modifier
                    .fillMaxWidth(0.91f)
                    .padding(horizontal = 16.dp)
            ) {
                dropDownList.forEach { option ->
                    DropdownMenuItem(
                        onClick = {
                            onAction(
                                ReportAction.ChangeTypeOfReport(option)
                            )
                            expanded = false
                        },
                        modifier = Modifier.padding(horizontal = 4.dp),
                        text = { CustomText(text = option) }
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            CustomText(
                text = stringResource(R.string.where),
                fontSize = 14,
                color = MediumGrey,
            )
            CustomTextField(
                textTitle = reportUiState.locationOfReportText,
                onValueChange = {
                    onAction(
                        ReportAction.ChangeLocationOfReport(it)
                    )
                },
                placerHolder = {
                    CustomText(
                        text = stringResource(R.string.location),
                        color = DarkGrey
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            CustomText(
                text = stringResource(R.string.title),
                fontSize = 14,
                color = MediumGrey,
            )
            CustomTextField(
                textTitle = reportUiState.titleOfReportText,
                onValueChange = {
                    onAction(
                        ReportAction.ChangeTitleOfReport(it)
                    )
                },
                placerHolder = {
                    CustomText(
                        text = stringResource(R.string.what_happened),
                        color = DarkGrey
                    )
                },
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            CustomText(
                text = stringResource(R.string.description),
                fontSize = 14,
                color = MediumGrey,
            )
            CustomTextField(
                textTitle = reportUiState.descriptionOfReportText,
                onValueChange = {
                    onAction(
                        ReportAction.ChangeDescriptionOfReport(it)
                    )
                },
                maxLines = 8,
                placerHolder = {
                    CustomText(
                        text = stringResource(R.string.give_more_information),
                        color = DarkGrey
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text)
            )
        }

        Column(
            verticalArrangement = Arrangement.Center,
        ) {
            CustomText(
                text = stringResource(R.string.add_image),
                fontSize = 14,
                color = MediumGrey,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(8.dp))
                    .background(DarkWhite),
                contentAlignment = Alignment.Center
            ) {
                if (reportUiState.imageUri == Uri.EMPTY){
                    Image(
                        painter = painterResource(id = R.drawable.gallery_add),
                        contentDescription = null,
                        Modifier
                            .padding(vertical = 36.dp)
                            .size(90.dp)
                            .clickable {
                                requestPermissionLauncher.launch(
                                    Manifest.permission.READ_EXTERNAL_STORAGE
                                )
                                pickImage.launch(context.getString(R.string.image))
                            },
                    )
                }else{
                    CoilImage(
                        data = reportUiState.imageUri.toString(),
                        modifier = Modifier
                            .padding(vertical = 8.dp, horizontal = 4.dp)
                            .size(145.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .clickable {
                                pickImage.launch(context.getString(R.string.image))
                            }
                    )
                }
            }
        }

        ElevatedButton(
            onClick = {
                onAction(
                    ReportAction.AddReportMarker(
                        Marker(
                            id = UUID.randomUUID().toString(),
                            latitude = reportUiState.latitude,
                            longitude = reportUiState.longitude,
                            title = reportUiState.titleOfReportText,
                            description = reportUiState.descriptionOfReportText,
                            type = reportUiState.typeOfReportText,
                            date = reportUiState.currentDate,
                            location = reportUiState.locationOfReportText,
                            time = reportUiState.currentTime,
                            imageUrl = reportUiState.imagesUrl,
                        )
                    )
                )
                onHomeNavigate()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.elevatedButtonColors(
                containerColor = MainRed20
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            CustomText(
                text = stringResource(R.string.publish),
                color = DarkWhite,
                modifier = Modifier.padding(vertical = 8.dp),
            )
        }
    }
}

@Preview
@Composable
fun PreviewOfReportDisaster() {
    ReportOptionsScreen(
        onHomeNavigate = {},
        reportUiState = ReportUiState(),
        onAction = {},
        dropDownList = listOf("Earthquake", "Flood", "Fire", "Storm", "Other")
    )
}