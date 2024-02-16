package com.gdscedirne.toplan.presentation.home.viewmodel

sealed class HomeAction {

    data class ChangeSosDialogState(val newState: Boolean) : HomeAction()
    data class ChangeCallDialogState(val newState: Boolean) : HomeAction()

}