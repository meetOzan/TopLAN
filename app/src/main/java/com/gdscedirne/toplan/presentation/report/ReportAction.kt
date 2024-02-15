package com.gdscedirne.toplan.presentation.report

import com.gdscedirne.toplan.data.model.Marker

sealed class ReportAction {
    data class ChangeSosDialogState(val newState: Boolean) : ReportAction()
    data class ChangeCallDialogState(val newState: Boolean) : ReportAction()
    data class ChangeLatLng(val latitude: Double, val longitude: Double) : ReportAction()
    data class AddReportMarker(val marker: Marker) : ReportAction()
    data class ChangeZoomLevel(val zoomLevel: Float) : ReportAction()
    data object GetCurrentDate : ReportAction()
    data object GetCurrentTime : ReportAction()
    data object ChangeIsErrorDialog : ReportAction()
    data object ChangeIsNoLocationRequestDialog : ReportAction()
    data class ChangeErrorMessage(val message: String) : ReportAction()
    data class ChangeMarkerAddedDialogState(val newState: Boolean) : ReportAction()
}