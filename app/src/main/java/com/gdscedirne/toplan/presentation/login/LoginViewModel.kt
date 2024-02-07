package com.gdscedirne.toplan.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gdscedirne.toplan.common.ResponseState
import com.gdscedirne.toplan.data.User
import com.gdscedirne.toplan.domain.repository.TopLanRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: TopLanRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState = _loginState.asStateFlow()

    fun onAction(action: LoginOnAction) {
        when (action) {
            is LoginOnAction.SignInEmailChanged -> onSignInEmailChanged(action.email)
            is LoginOnAction.SignInPasswordChanged -> onSignInPasswordChanged(action.password)
            is LoginOnAction.SignUpEmailChanged -> onSignUpEmailChanged(action.email)
            is LoginOnAction.SignUpPasswordChanged -> onSignUpPasswordChanged(action.password)
            is LoginOnAction.NumberChanged -> onNumberChanged(action.number)
            is LoginOnAction.SignInUser -> signInUserWithEmailAndPassword(
                action.user,
                action.onNavigate
            )

            is LoginOnAction.SignUpUser -> signUpUseWithEmailAndPassword(
                action.user,
                action.onNavigate
            )
        }
    }

    private fun signUpUseWithEmailAndPassword(user: User, onNavigate: () -> Unit) {
        viewModelScope.launch {
            repository.signUpWithEmailAndPassword(user, onNavigate).collect { response ->
                when (response) {
                    is ResponseState.Loading -> {
                        _loginState.value = _loginState.value.copy(isLoading = true)
                    }

                    is ResponseState.Success -> {
                        _loginState.value = _loginState.value.copy(isLoading = false)
                    }

                    is ResponseState.Error -> {
                        _loginState.value = _loginState.value.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = response.message
                        )
                    }
                }
            }
        }
    }

    private fun signInUserWithEmailAndPassword(user: User, onNavigate: () -> Unit) {
        viewModelScope.launch {
            repository.signInUserWithEmailAndPassword(user, onNavigate).collect { response ->
                when (response) {
                    is ResponseState.Loading -> {
                        _loginState.value = _loginState.value.copy(isLoading = true)
                    }

                    is ResponseState.Success -> {
                        _loginState.value = _loginState.value.copy(isLoading = false)
                    }

                    is ResponseState.Error -> {
                        _loginState.value = _loginState.value.copy(
                            isLoading = false,
                            isError = true,
                            errorMessage = response.message
                        )
                    }
                }
            }
        }
    }

    private fun onSignUpEmailChanged(email: String) {
        _loginState.value = _loginState.value.copy(signUpEmail = email)
    }

    private fun onSignInEmailChanged(email: String) {
        _loginState.value = _loginState.value.copy(signInEmail = email)
    }

    private fun onSignUpPasswordChanged(password: String) {
        _loginState.value = _loginState.value.copy(signUpPassword = password)
    }

    private fun onSignInPasswordChanged(password: String) {
        _loginState.value = _loginState.value.copy(signInPassword = password)
    }

    private fun onNumberChanged(number: String) {
        _loginState.value = _loginState.value.copy(number = number)
    }
}

data class LoginUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val signInEmail: String = "",
    var signInPassword: String = "",
    val signUpEmail: String = "",
    var signUpPassword: String = "",
    val number: String = ""
)