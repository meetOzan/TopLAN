package com.gdscedirne.toplan.presentation.profile.viewmodel

import android.content.Context
import android.net.Uri
import com.gdscedirne.toplan.data.model.User

sealed class ProfileAction {
    data class ChangeLoadingState(val isLoading: Boolean) : ProfileAction()
    data class ChangeErrorMessageState(val errorMessage: Boolean) : ProfileAction()
    data object GetUser : ProfileAction()
    data class ChangeEmail(val email: String) : ProfileAction()
    data class ChangePhoneNumber(val phoneNumber: String) : ProfileAction()
    data class ChangeAddress(val address: String) : ProfileAction()
    data class ChangeRelativeName(val relativeName: String) : ProfileAction()
    data class ChangeName(val name: String) : ProfileAction()
    data class ChangeSurname(val surname: String) : ProfileAction()
    data class ChangeEditProfileUser(
        val name: String,
        val surname: String,
        val email: String,
        val phoneNumber: String,
        val address: String,
        val relativeName: String
    ) : ProfileAction()
    data class UpdateProfile(val user: User, val onHomeNavigate: () -> Unit) : ProfileAction()
    data class UploadImageStorage(
        val uri: Uri,
        val context: Context,
        val onSuccess: (String, String) -> Unit,
        val onFailure: (String) -> Unit,
    ) : ProfileAction()
    data class SetImageUri(val uri: Uri, val url: String) : ProfileAction()

}