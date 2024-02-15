package com.gdscedirne.toplan.presentation.report

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ReportViewModel @Inject constructor() : ViewModel(){

    private val _reportState = MutableStateFlow(ReportUiState.initial)
    val reportState = _reportState.asStateFlow()

    fun onAction(action: ReportAction){
        when(action){
            is ReportAction.ChangeSosDialogState -> changeSosDialogState(action.newState)
            is ReportAction.ChangeCallDialogState -> changeCallDialogState(action.newState)
        }
    }

    private fun changeSosDialogState(newState: Boolean){
        _reportState.value = _reportState.value.copy(sosCallDialog = newState)
    }

    private fun changeCallDialogState(newState: Boolean){
        _reportState.value = _reportState.value.copy(isSosDialogOpen = newState)
    }

}

data class ReportUiState(
    val isLoading: Boolean = false,
    val errorState: Boolean = false,
    val message: String = "",
    val sosCallDialog : Boolean = false,
    val isSosDialogOpen: Boolean = false
){
    companion object{
        val initial = ReportUiState(isLoading = true)
    }
}