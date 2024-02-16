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
    data class ChangeTypeOfReport(val type: String) : ReportAction()
    data class ChangeLocationOfReport(val location: String) : ReportAction()
    data class ChangeTitleOfReport(val title: String) : ReportAction()
    data class ChangeDescriptionOfReport(val description: String) : ReportAction()
    data object GetDropdownList : ReportAction()

}