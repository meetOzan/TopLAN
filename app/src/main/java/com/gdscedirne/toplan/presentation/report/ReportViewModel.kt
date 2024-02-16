package com.gdscedirne.toplan.presentation.report

import android.content.Context
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdscedirne.toplan.common.Constants.ARGS_OPTION
import com.gdscedirne.toplan.common.GatheringAid
import com.gdscedirne.toplan.common.MarkerType
import com.gdscedirne.toplan.common.ReportOptions
import com.gdscedirne.toplan.common.ResponseState
import com.gdscedirne.toplan.common.SuppliesEquipment
import com.gdscedirne.toplan.data.model.Marker
import com.gdscedirne.toplan.domain.repository.TopLanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val repository: TopLanRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _reportState = MutableStateFlow(ReportUiState())
    val reportState = _reportState.asStateFlow()

    private val reportOption = savedStateHandle.get<String>(key = ARGS_OPTION)

    fun onAction(action: ReportAction) {
        when (action) {
            is ReportAction.ChangeSosDialogState -> changeSosDialogState(action.newState)
            is ReportAction.ChangeCallDialogState -> changeCallDialogState(action.newState)
            is ReportAction.AddReportMarker -> addReportMarker(action.marker)
            is ReportAction.ChangeLatLng -> changeLatLng(action.latitude, action.longitude)
            is ReportAction.ChangeZoomLevel -> changeZoomLevel(action.zoomLevel)
            ReportAction.GetCurrentDate -> getCurrentDate()
            ReportAction.GetCurrentTime -> getCurrentTime()
            ReportAction.ChangeIsErrorDialog -> changeIsErrorDialog()
            ReportAction.ChangeIsNoLocationRequestDialog -> changeIsNoLocationRequestDialog()
            is ReportAction.ChangeErrorMessage -> changeErrorMessage(action.message)
            is ReportAction.ChangeMarkerAddedDialogState -> changeMarkerAddedDialogState(action.newState)
            is ReportAction.ChangeTypeOfReport -> changeTypeOfReportText(action.type)
            is ReportAction.ChangeLocationOfReport -> changeLocationOfReportText(action.location)
            is ReportAction.ChangeTitleOfReport -> changeTitleOfReportText(action.title)
            is ReportAction.ChangeDescriptionOfReport -> changeDescriptionOfReportText(action.description)
            ReportAction.GetDropdownList -> getDropdownList()
            is ReportAction.SetImageUri -> setImageUri(action.uri, action.url)
            is ReportAction.UploadImageFirestore -> TODO()
            is ReportAction.UploadImageStorage -> uploadImageStorage(
                uri = action.uri,
                context = action.context,
                onSuccess = action.onSuccess,
                onFailure = action.onFailure
            )
        }
    }

    private fun changeReportAction() {
        _reportState.value = _reportState.value.copy(
            reportOptions = reportOption.orEmpty()
        )
    }

    private fun getDropdownList() {
        viewModelScope.launch {
            changeReportAction()
            when (reportOption) {
                ReportOptions.DISASTER.name -> {
                    _reportState.value = _reportState.value.copy(
                        dropDownMenu = MarkerType.entries.map { it.name }
                    )
                }

                ReportOptions.SUPPLIES_EQUIPMENT.name -> {
                    _reportState.value = _reportState.value.copy(
                        dropDownMenu = SuppliesEquipment.entries.map { it.name }
                    )
                }

                ReportOptions.GATHERING_AID.name -> {
                    _reportState.value = _reportState.value.copy(
                        dropDownMenu = GatheringAid.entries.map { it.name }
                    )
                }

                ReportOptions.HELP.name -> {
                    _reportState.value = _reportState.value.copy(
                        dropDownMenu = emptyList()
                    )
                }
            }
        }
    }

    private fun addReportMarker(marker: Marker) {
        viewModelScope.launch {
            repository.addMarker(marker).collect { responseState ->
                when (responseState) {
                    is ResponseState.Loading -> {
                        _reportState.value = _reportState.value.copy(isLoading = true)
                    }

                    is ResponseState.Error -> {
                        _reportState.value = _reportState.value.copy(
                            errorState = true,
                            message = responseState.message,
                            isLoading = false
                        )
                    }

                    is ResponseState.Success -> {
                        _reportState.value = _reportState.value.copy(
                            errorState = false,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    private fun changeSosDialogState(newState: Boolean) {
        _reportState.value = _reportState.value.copy(sosCallDialog = newState)
    }

    private fun changeCallDialogState(newState: Boolean) {
        _reportState.value = _reportState.value.copy(isSosDialogOpen = newState)
    }

    private fun changeLatLng(latitude: Double, longitude: Double) {
        _reportState.value = _reportState.value.copy(latitude = latitude, longitude = longitude)
    }

    private fun changeZoomLevel(zoomLevel: Float) {
        _reportState.value = _reportState.value.copy(zoomLevel = zoomLevel)
    }

    private fun getCurrentDate() {
        _reportState.value = _reportState.value.copy(
            currentDate = repository.getCurrentDate()
        )
    }

    private fun getCurrentTime() {
        _reportState.value = _reportState.value.copy(
            currentTime = repository.getCurrentTime()
        )
    }

    private fun changeIsErrorDialog() {
        _reportState.value =
            _reportState.value.copy(isErrorDialogOpen = !_reportState.value.isErrorDialogOpen)
    }

    private fun changeIsNoLocationRequestDialog() {
        _reportState.value =
            _reportState.value.copy(isNoLocationRequestDialogOpen = !_reportState.value.isNoLocationRequestDialogOpen)
    }

    private fun changeErrorMessage(message: String) {
        _reportState.value = _reportState.value.copy(errorMessage = message)
    }

    private fun changeMarkerAddedDialogState(newState: Boolean) {
        _reportState.value = _reportState.value.copy(markerAddedDialog = newState)
    }

    private fun changeTypeOfReportText(text: String) {
        _reportState.value = _reportState.value.copy(typeOfReportText = text)
    }

    private fun changeLocationOfReportText(text: String) {
        _reportState.value = _reportState.value.copy(locationOfReportText = text)
    }

    private fun changeTitleOfReportText(text: String) {
        _reportState.value = _reportState.value.copy(titleOfReportText = text)
    }

    private fun changeDescriptionOfReportText(text: String) {
        _reportState.value = _reportState.value.copy(descriptionOfReportText = text)
    }

    private fun setImageUri(uri: Uri, url: String) {
        with(_reportState.value) {
            _reportState.value = this.copy(
                imagesUrl = url,
                imageUri = uri
            )
        }
    }

    private fun uploadImageStorage(
        uri: Uri, context: Context,
        onSuccess: (String, String) -> Unit = { _, _ -> },
        onFailure: (String) -> Unit = { _ -> },
    ) {
        viewModelScope.launch {
            repository.uploadImageToStorage(uri, context, onSuccess, onFailure)
                .collect { responseState ->
                    when (responseState) {
                        is ResponseState.Loading -> {
                            _reportState.value = _reportState.value.copy(isLoading = true)
                        }

                        is ResponseState.Error -> {
                            _reportState.value = _reportState.value.copy(
                                errorState = true,
                                message = responseState.message,
                                isLoading = false
                            )
                        }

                        is ResponseState.Success -> {
                            _reportState.value = _reportState.value.copy(
                                errorState = false,
                                isLoading = false
                            )
                        }
                    }
                }
        }
    }

}

data class ReportUiState(
    val isLoading: Boolean = false,
    val errorState: Boolean = false,
    val message: String = "",
    val errorMessage: String = "",
    val sosCallDialog: Boolean = false,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val zoomLevel: Float = 15f,
    val currentDate: String = "",
    val currentTime: String = "",
    val isErrorDialogOpen: Boolean = false,
    val isNoLocationRequestDialogOpen: Boolean = false,
    val markerAddedDialog: Boolean = false,
    val isSosDialogOpen: Boolean = false,
    val typeOfReportText: String = "",
    val locationOfReportText: String = "",
    val titleOfReportText: String = "",
    val descriptionOfReportText: String = "",
    val reportOptions: String = "",
    val dropDownMenu: List<String> = listOf(),
    var imageUri: Uri = Uri.EMPTY,
    val imagesUrl: String = "",
)