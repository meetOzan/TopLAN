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

    private val _loginState = MutableStateFlow(LoginUiState.initial())
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

            is LoginOnAction.PagerChanged -> onPagerStateChanged(action.position)
            is LoginOnAction.NameChanged -> onSignUpNameChanged(action.name)
            is LoginOnAction.SurnameChanged -> onSignUpSurnameChanged(action.surname)
            is LoginOnAction.RelativeNameChanged -> onSignUpRelativeNameChanged(action.relation)
            is LoginOnAction.AddressChanged -> onSignUpAddressChanged(action.address)
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

    private fun onSignInEmailChanged(email: String) {
        _loginState.value = _loginState.value.copy(signInEmail = email)
    }

    private fun onSignInPasswordChanged(password: String) {
        _loginState.value = _loginState.value.copy(signInPassword = password)
    }

    private fun onSignUpEmailChanged(email: String) {
        _loginState.value = _loginState.value.copy(signUpEmail = email)
    }

    private fun onSignUpPasswordChanged(password: String) {
        _loginState.value = _loginState.value.copy(signUpPassword = password)
    }

    private fun onSignUpNameChanged(name: String) {
        _loginState.value = _loginState.value.copy(signUpName = name)
    }

    private fun onSignUpSurnameChanged(surname: String) {
        _loginState.value = _loginState.value.copy(signUpSurname = surname)
    }

    private fun onSignUpRelativeNameChanged(relativeName: String) {
        _loginState.value = _loginState.value.copy(signUpRelativeName = relativeName)
    }

    private fun onSignUpAddressChanged(address: String) {
        _loginState.value = _loginState.value.copy(signUpAddress = address)
    }

    private fun onNumberChanged(number: String) {
        _loginState.value = _loginState.value.copy(number = number)
    }

    private fun onPagerStateChanged(pagerState: Int) {
        _loginState.value = _loginState.value.copy(pagerState = pagerState)
    }
}

data class LoginUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val errorMessage: String = "",
    val signInEmail: String = "",
    var signInPassword: String = "",
    val signUpEmail: String = "",
    val signUpPassword: String = "",
    val signUpName: String = "",
    val signUpSurname: String = "",
    val signUpRelativeName: String = "",
    val signUpAddress: String = "",
    val number: String = "",
    val pagerState: Int = 1
) {
    companion object {
        fun initial() = LoginUiState(isLoading = true)
    }
}