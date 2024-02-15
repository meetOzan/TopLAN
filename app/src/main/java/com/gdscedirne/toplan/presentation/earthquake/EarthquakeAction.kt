package com.gdscedirne.toplan.presentation.earthquake

sealed class EarthquakeAction {

    data object ChangeIsErrorDialog : EarthquakeAction()
    data object ChangeIsNoLocationRequestDialog : EarthquakeAction()
    data class ChangeErrorMessage(val errorMessage: String) : EarthquakeAction()
    data class ChangeLatLng(val latitude: Double, val longitude: Double) : EarthquakeAction()
    data class ChangeZoomLevel(val zoomLevel: Float) : EarthquakeAction()
    data object GetMarkers : EarthquakeAction()

}