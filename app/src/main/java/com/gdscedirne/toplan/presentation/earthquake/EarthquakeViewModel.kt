package com.gdscedirne.toplan.presentation.earthquake

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EarthquakeViewModel @Inject constructor() : ViewModel() {

    private val _earthquakeUiState = MutableStateFlow(EarthquakeUiState.initial())
    val earthquakeUiState = _earthquakeUiState.asStateFlow()


    fun onAction(onAction: EarthquakeAction) {
        when (onAction) {
            is EarthquakeAction.ChangeIsErrorDialog -> changeIsErrorDialog()
            is EarthquakeAction.ChangeIsNoLocationRequestDialog -> changeIsNoLocationRequestDialog()
            is EarthquakeAction.ChangeErrorMessage -> changeErrorMessage(onAction.errorMessage)
            is EarthquakeAction.ChangeLatLng -> changeLatLng(onAction.latitude, onAction.longitude)
            is EarthquakeAction.ChangeZoomLevel -> changeZoomLevel(onAction.zoomLevel)
        }
    }

    private fun changeIsErrorDialog() {
        _earthquakeUiState.value =
            _earthquakeUiState.value.copy(isErrorDialog = !_earthquakeUiState.value.isErrorDialog)
    }

    private fun changeIsNoLocationRequestDialog() {
        _earthquakeUiState.value =
            _earthquakeUiState.value.copy(isNoLocationRequestDialog = !_earthquakeUiState.value.isNoLocationRequestDialog)
    }

    private fun changeErrorMessage(errorMessage: String) {
        _earthquakeUiState.value = _earthquakeUiState.value.copy(errorMessage = errorMessage)
    }

    private fun changeLatLng(latitude: Double, longitude: Double) {
        _earthquakeUiState.value = _earthquakeUiState.value.copy(latitude = latitude, longitude = longitude)
    }

    private fun changeZoomLevel(zoomLevel: Float) {
        _earthquakeUiState.value = _earthquakeUiState.value.copy(zoomLevel = zoomLevel)
    }

}

data class EarthquakeUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val isErrorDialog: Boolean = false,
    val isNoLocationRequestDialog: Boolean = false,
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val zoomLevel :  Float = 15f
) {
    companion object {
        fun initial() = EarthquakeUiState(isLoading = true)
    }
}