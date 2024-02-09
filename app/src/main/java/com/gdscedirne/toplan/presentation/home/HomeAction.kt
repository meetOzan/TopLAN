package com.gdscedirne.toplan.presentation.home

sealed class HomeAction {

    data class ChangeSosDialogState(val newState: Boolean) : HomeAction()

}