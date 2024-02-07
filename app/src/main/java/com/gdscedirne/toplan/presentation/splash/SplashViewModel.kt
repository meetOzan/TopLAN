package com.gdscedirne.toplan.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdscedirne.toplan.common.ResponseState
import com.gdscedirne.toplan.domain.repository.TopLanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: TopLanRepository
) : ViewModel() {

    private val _splashUiState = MutableStateFlow(SplashUiState.initial())
    val splashUiState = _splashUiState.asStateFlow()

    fun onAction(action: SplashAction) {
        when (action) {
            is SplashAction.CheckUserSession -> checkUserSession()
        }
    }

    private fun checkUserSession() {
        viewModelScope.launch {
            repository.isUserSignedIn().collect { response ->
                when (response) {
                    is ResponseState.Loading -> {
                        _splashUiState.value = _splashUiState.value.copy(isLoading = true)
                    }

                    is ResponseState.Success -> {
                        _splashUiState.value = _splashUiState.value.copy(
                            isLoading = false,
                            isUserSignedIn = response.data
                        )
                    }

                    is ResponseState.Error -> {
                        _splashUiState.value = _splashUiState.value.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = response.message
                        )
                    }
                }
            }
        }
    }

}

data class SplashUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isUserSignedIn: Boolean = false,
    val errorMessage: String = ""
) {
    companion object {
        fun initial() = SplashUiState(isLoading = true)
    }
}