package com.gdscedirne.toplan.presentation.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel(){

    private val _homeState = MutableStateFlow(HomeUiState.initial)
    val homeState = _homeState.asStateFlow()

    fun onAction(action: HomeAction){
        when(action){
            is HomeAction.ChangeSosDialogState -> changeSosDialogState(action.newState)
            is HomeAction.ChangeCallDialogState -> changeCallDialogState(action.newState)
        }
    }

    private fun changeSosDialogState(newState: Boolean){
        _homeState.value = _homeState.value.copy(sosCallDialog = newState)
    }

    private fun changeCallDialogState(newState: Boolean){
        _homeState.value = _homeState.value.copy(isSosDialogOpen = newState)
    }

}

data class HomeUiState(
    val isLoading: Boolean = false,
    val errorState: Boolean = false,
    val message: String = "",
    val sosCallDialog : Boolean = false,
    val isSosDialogOpen: Boolean = false
){
    companion object{
        val initial = HomeUiState(isLoading = true)
    }
}