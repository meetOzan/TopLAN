package com.gdscedirne.toplan.presentation.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel(){

    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState = _loginState.asStateFlow()

    fun onAction(action: LoginOnAction){
        when(action){
            is LoginOnAction.EmailChanged -> onEmailChanged(action.email)
            is LoginOnAction.PasswordChanged -> onPasswordChanged(action.password)
            is LoginOnAction.NumberChanged -> onNumberChanged(action.number)
        }
    }

    private fun onEmailChanged(email: String) {
        _loginState.value = _loginState.value.copy(email = email)
    }

    private fun onPasswordChanged(password: String) {
        _loginState.value = _loginState.value.copy(password = password)
    }

    private fun onNumberChanged(number: String) {
        _loginState.value = _loginState.value.copy(number = number)
    }
}

data class LoginUiState(
    val email: String = "",
    var password: String = "",
    val number: String = "",
    val isLoading: Boolean = false,
    val error: String = ""
)