package com.gdscedirne.toplan.presentation.report

sealed class ReportAction {
    data class ChangeSosDialogState(val newState: Boolean) : ReportAction()
    data class ChangeCallDialogState(val newState: Boolean) : ReportAction()
}