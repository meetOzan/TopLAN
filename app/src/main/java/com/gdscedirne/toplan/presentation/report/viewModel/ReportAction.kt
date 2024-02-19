package com.gdscedirne.toplan.presentation.report.viewModel

import android.content.Context
import android.net.Uri
import com.gdscedirne.toplan.data.model.Feed
import com.gdscedirne.toplan.data.model.Marker

sealed class ReportAction {
    data class ChangeSosDialogState(val newState: Boolean) : ReportAction()
    data class ChangeCallDialogState(val newState: Boolean) : ReportAction()
    data class ChangeLatLng(val latitude: Double, val longitude: Double) : ReportAction()
    data class AddReportMarker(val marker: Marker, val onNavigate: () -> Unit) : ReportAction()
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
    data class UploadImageStorage(
        val uri: Uri,
        val context: Context,
        val onSuccess: (String, String) -> Unit,
        val onFailure: (String) -> Unit,
    ) : ReportAction()
    data class SetImageUri(val uri: Uri, val url: String) : ReportAction()
    data class UploadImageFirestore(
        val imagesUrl: List<String>,
        val imageName: String,
        val onSuccess: () -> Unit,
        val onFailure: (String) -> Unit,
    ) : ReportAction()
    data class AddFeed(val feed: Feed) : ReportAction()
}