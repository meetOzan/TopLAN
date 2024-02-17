package com.gdscedirne.toplan.presentation.profile.viewmodel

sealed class ProfileOnAction {
    data class ChangeLoadingState(val isLoading: Boolean) : ProfileOnAction()
    data class ChangeErrorMessageState(val errorMessage: Boolean) : ProfileOnAction()
    data object GetUser : ProfileOnAction()
    data class ChangeEmail(val email: String) : ProfileOnAction()
    data class ChangePhoneNumber(val phoneNumber: String) : ProfileOnAction()
    data class ChangeName(val name: String) : ProfileOnAction()
    data class ChangeAddress(val address: String) : ProfileOnAction()

}