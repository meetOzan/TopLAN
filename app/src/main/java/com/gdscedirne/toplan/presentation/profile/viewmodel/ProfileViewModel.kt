package com.gdscedirne.toplan.presentation.profile.viewmodel

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
) : ViewModel(){

    private val _profileState = MutableStateFlow(ProfileUiState.initial)
    val profileState = _profileState.asStateFlow()

    fun onAction(action: ProfileOnAction){
        when(action){
            is ProfileOnAction.ChangeLoadingState -> changeLoadingState(action.isLoading)
            is ProfileOnAction.ChangeErrorMessageState -> changeErrorMessageState(action.errorMessage)
            ProfileOnAction.GetUser -> getUserData()
            is ProfileOnAction.ChangeAddress -> changeAddress(action.address)
            is ProfileOnAction.ChangeEmail -> changeEmail(action.email)
            is ProfileOnAction.ChangeName -> changeFullName(action.name)
            is ProfileOnAction.ChangePhoneNumber -> changePhoneNumber(action.phoneNumber)
        }
    }

    private fun getUserData(){
        viewModelScope.launch {
            repository.getUser().collect { response ->
                when(response){
                    is ResponseState.Loading -> {
                        _profileState.value = _profileState.value.copy(isLoading = true)
                    }
                    is ResponseState.Success -> {
                        _profileState.value = _profileState.value.copy(user = response.data, isLoading = false)
                    }
                    is ResponseState.Error -> {
                        _profileState.value = _profileState.value.copy(isError = true, isLoading = false, message = response.message!!)
                    }
                }
            }
        }
    }

    private fun changeLoadingState(isLoading: Boolean){
        _profileState.value =  _profileState.value.copy(isLoading = isLoading)
    }

    private fun changeErrorMessageState(errorMessage: Boolean){
        _profileState.value = _profileState.value.copy(errorState = errorMessage)
    }

    private fun changeEmail(email: String){
        _profileState.value = _profileState.value.copy(email = email)
    }

    private fun changePhoneNumber(phoneNumber: String){
        _profileState.value = _profileState.value.copy(phoneNumber = phoneNumber)
    }

    private fun changeFullName(fullName: String){
        _profileState.value = _profileState.value.copy(fullName = fullName)
    }

    private fun changeAddress(address: String){
        _profileState.value = _profileState.value.copy(address = address)
    }

}

data class ProfileUiState(
    val isLoading: Boolean = false,
    val isError : Boolean = false,
    val errorState: Boolean = false,
    val message: String = "",
    val user: User = User(),
    val email: String = "",
    val phoneNumber: String = "",
    val fullName: String = "",
    val address: String = "",
){
    companion object{
        val initial = ProfileUiState(isLoading = true)
    }
}