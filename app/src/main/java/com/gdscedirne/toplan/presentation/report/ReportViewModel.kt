package com.gdscedirne.toplan.presentation.report

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdscedirne.toplan.common.ResponseState
import com.gdscedirne.toplan.data.model.Marker
import com.gdscedirne.toplan.domain.repository.TopLanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor(
    private val repository: TopLanRepository
) : ViewModel(){

    private val _reportState = MutableStateFlow(ReportUiState())
    val reportState = _reportState.asStateFlow()

    fun onAction(action: ReportAction){
        when(action){
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
        }
    }

     private fun addReportMarker(marker: Marker){
        viewModelScope.launch {
            repository.addMarker(marker).collect { responseState ->
                when(responseState){
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

    private fun changeSosDialogState(newState: Boolean){
        _reportState.value = _reportState.value.copy(sosCallDialog = newState)
    }

    private fun changeCallDialogState(newState: Boolean){
        _reportState.value = _reportState.value.copy(isSosDialogOpen = newState)
    }

    private fun changeLatLng(latitude: Double, longitude: Double) {
        _reportState.value = _reportState.value.copy(latitude = latitude, longitude = longitude)
    }

    private fun changeZoomLevel(zoomLevel: Float){
        _reportState.value = _reportState.value.copy(zoomLevel = zoomLevel)
    }

    private fun getCurrentDate(){
        _reportState.value = _reportState.value.copy(
            currentDate = repository.getCurrentDate()
        )
    }

    private fun getCurrentTime(){
        _reportState.value = _reportState.value.copy(
            currentTime = repository.getCurrentTime()
        )
    }

    private fun changeIsErrorDialog(){
        _reportState.value = _reportState.value.copy(isErrorDialogOpen = !_reportState.value.isErrorDialogOpen)
    }

    private fun changeIsNoLocationRequestDialog(){
        _reportState.value = _reportState.value.copy(isNoLocationRequestDialogOpen = !_reportState.value.isNoLocationRequestDialogOpen)
    }

    private fun changeErrorMessage(message: String){
        _reportState.value = _reportState.value.copy(errorMessage = message)
    }

    private fun changeMarkerAddedDialogState(newState: Boolean){
        _reportState.value = _reportState.value.copy(markerAddedDialog = newState)
    }
}

data class ReportUiState(
    val isLoading: Boolean = false,
    val errorState: Boolean = false,
    val message: String = "",
    val errorMessage: String = "",
    val sosCallDialog : Boolean = false,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val zoomLevel :  Float = 15f,
    val currentDate : String = "",
    val currentTime : String = "",
    val isErrorDialogOpen: Boolean = false,
    val isNoLocationRequestDialogOpen: Boolean = false,
    val markerAddedDialog: Boolean = false,
    val isSosDialogOpen: Boolean = false,
)