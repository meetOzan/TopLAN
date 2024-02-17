package com.gdscedirne.toplan.presentation.home.viewmodel

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
class HomeViewModel @Inject constructor(
    private val repository: TopLanRepository
) : ViewModel() {

    private val _homeState = MutableStateFlow(HomeUiState.initial)
    val homeState = _homeState.asStateFlow()

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.ChangeSosDialogState -> changeSosDialogState(action.newState)
            is HomeAction.ChangeCallDialogState -> changeCallDialogState(action.newState)
            HomeAction.GetUser -> getUser()
            is HomeAction.SignOut -> signOut(action.onNavigate)
        }
    }

    private fun changeSosDialogState(newState: Boolean) {
        _homeState.value = _homeState.value.copy(sosCallDialog = newState)
    }

    private fun changeCallDialogState(newState: Boolean) {
        _homeState.value = _homeState.value.copy(isSosDialogOpen = newState)
    }

    private fun signOut(onNavigate: () -> Unit) {
        viewModelScope.launch {
            repository.signOut().collect() { response ->
                when (response) {
                    is ResponseState.Error -> {
                        _homeState.value = _homeState.value.copy(
                            isLoading = false,
                            errorState = true,
                            message = response.message
                        )
                    }

                    ResponseState.Loading -> {
                        _homeState.value = _homeState.value.copy(isLoading = true)
                    }

                    is ResponseState.Success -> {
                        _homeState.value = _homeState.value.copy(
                            isLoading = false,
                            errorState = false
                        )
                        onNavigate()
                    }
                }
            }
        }
    }

    private fun getUser() {
        viewModelScope.launch {
            repository.getUser().collect() { response ->
                when (response) {
                    is ResponseState.Error -> {
                        _homeState.value = _homeState.value.copy(
                            isLoading = false,
                            errorState = true,
                            message = response.message
                        )
                    }

                    ResponseState.Loading -> {
                        _homeState.value = _homeState.value.copy(isLoading = true)
                    }

                    is ResponseState.Success -> {
                        _homeState.value = _homeState.value.copy(
                            isLoading = false,
                            errorState = false,
                            user = response.data
                        )
                    }
                }
            }
        }
    }

}

data class HomeUiState(
    val isLoading: Boolean = false,
    val errorState: Boolean = false,
    val message: String = "",
    val sosCallDialog: Boolean = false,
    val isSosDialogOpen: Boolean = false,
    val user: User = User()
) {
    companion object {
        val initial = HomeUiState(isLoading = true)
    }
}
