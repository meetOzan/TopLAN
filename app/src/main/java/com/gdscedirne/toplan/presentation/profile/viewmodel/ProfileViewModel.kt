package com.gdscedirne.toplan.presentation.profile.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdscedirne.toplan.common.ResponseState
import com.gdscedirne.toplan.data.model.User
import com.gdscedirne.toplan.domain.repository.TopLanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: TopLanRepository
) : ViewModel() {

    private val _profileState = MutableStateFlow(ProfileUiState.initial)
    val profileState = _profileState.asStateFlow()

    fun onAction(action: ProfileAction) {
        when (action) {
            is ProfileAction.ChangeLoadingState -> changeLoadingState(action.isLoading)
            is ProfileAction.ChangeErrorMessageState -> changeErrorMessageState(action.errorMessage)
            ProfileAction.GetUser -> getUserData()
            is ProfileAction.ChangeAddress -> changeAddress(action.address)
            is ProfileAction.ChangeEmail -> changeEmail(action.email)
            is ProfileAction.ChangeRelativeName -> changeRelativeName(action.relativeName)
            is ProfileAction.ChangeSurname -> changeSurname(action.surname)
            is ProfileAction.ChangeEditProfileUser -> changeEditProfileUser(
                action.name,
                action.surname,
                action.email,
                action.phoneNumber,
                action.address,
                action.relativeName
            )
            is ProfileAction.ChangeName -> changeName(action.name)
            is ProfileAction.ChangePhoneNumber -> changePhoneNumber(action.phoneNumber)
            is ProfileAction.UpdateProfile -> updateProfile(action.user, action.onHomeNavigate)
            is ProfileAction.SetImageUri -> setImageUri(action.uri, action.url)
            is ProfileAction.UploadImageStorage -> uploadImageStorage(
                uri = action.uri,
                context = action.context,
                onSuccess = action.onSuccess,
                onFailure = action.onFailure
            )
        }
    }

    private fun getUserData() {
        viewModelScope.launch {
            repository.getUser().collect { response ->
                when (response) {
                    is ResponseState.Loading -> {
                        _profileState.value = _profileState.value.copy(isLoading = true)
                    }

                    is ResponseState.Success -> {
                        _profileState.value =
                            _profileState.value.copy(user = response.data, isLoading = false)
                    }

                    is ResponseState.Error -> {
                        _profileState.value = _profileState.value.copy(
                            isError = true,
                            isLoading = false,
                            message = response.message
                        )
                    }
                }
            }
        }
    }

    private fun updateProfile(user: User, onNavigate: () -> Unit) {
        viewModelScope.launch {
            repository.updateProfile(user).collect { response ->
                when (response) {
                    is ResponseState.Loading -> {
                        _profileState.value = _profileState.value.copy(isLoading = true)
                    }

                    is ResponseState.Success -> {
                        _profileState.value = _profileState.value.copy(isLoading = false)
                        onNavigate()
                    }

                    is ResponseState.Error -> {
                        _profileState.value = _profileState.value.copy(
                            isError = true,
                            isLoading = false,
                            message = response.message!!
                        )
                    }
                }
            }
        }
    }

    private fun changeLoadingState(isLoading: Boolean) {
        _profileState.value = _profileState.value.copy(isLoading = isLoading)
    }

    private fun changeErrorMessageState(errorMessage: Boolean) {
        _profileState.value = _profileState.value.copy(errorState = errorMessage)
    }

    private fun changeEmail(email: String) {
        _profileState.value = _profileState.value.copy(email = email)
    }

    private fun changePhoneNumber(phoneNumber: String) {
        _profileState.value = _profileState.value.copy(phoneNumber = phoneNumber)
    }

    private fun changeAddress(address: String) {
        _profileState.value = _profileState.value.copy(address = address)
    }

    private fun changeRelativeName(relativeName: String) {
        _profileState.value = _profileState.value.copy(relativeName = relativeName)
    }

    private fun changeName(name: String) {
        _profileState.value = _profileState.value.copy(name = name)
    }

    private fun changeSurname(surname: String) {
        _profileState.value = _profileState.value.copy(surname = surname)
    }

    private fun changeEditProfileUser(
        name: String,
        surname: String,
        email: String,
        phoneNumber: String,
        address: String,
        relativeName: String
    ) {
        _profileState.value = _profileState.value.copy(
            name = name,
            surname = surname,
            email = email,
            phoneNumber = phoneNumber,
            address = address,
            relativeName = relativeName
        )
    }

    private fun setImageUri(uri: Uri, url: String) {
        with(_profileState.value) {
            _profileState.value = this.copy(
                imageUrl = url,
                imageUri = uri
            )
        }
    }

    private fun uploadImageStorage(
        uri: Uri, context: Context,
        onSuccess: (String, String) -> Unit = { _, _ -> },
        onFailure: (String) -> Unit = { _ -> },
    ) {
        viewModelScope.launch {
            repository.uploadImageToStorage(uri, context, onSuccess, onFailure)
                .collect { responseState ->
                    when (responseState) {
                        is ResponseState.Loading -> {
                            _profileState.value = _profileState.value.copy(isLoading = true)
                        }

                        is ResponseState.Error -> {
                            _profileState.value = _profileState.value.copy(
                                errorState = true,
                                message = responseState.message,
                                isLoading = false
                            )
                        }

                        is ResponseState.Success -> {
                            _profileState.value = _profileState.value.copy(
                                errorState = false,
                                isLoading = false
                            )
                        }
                    }
                }
        }
    }

}

data class ProfileUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorState: Boolean = false,
    val message: String = "",
    val user: User = User(),
    val email: String = "",
    val name: String = "",
    val surname: String = "",
    val phoneNumber: String = "",
    val relativeName: String = "",
    val fullName: String = "",
    val address: String = "",
    val imageUrl: String = "",
    val imageUri: Uri = Uri.EMPTY
) {
    companion object {
        val initial = ProfileUiState(isLoading = true)
    }
}