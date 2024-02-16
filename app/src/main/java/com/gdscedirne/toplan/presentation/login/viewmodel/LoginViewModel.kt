package com.gdscedirne.toplan.presentation.login.viewmodel

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
                action.email,
                action.password,
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
            is LoginOnAction.ChangeLoadingState -> isLoadingChanged(action.isLoading)
            is LoginOnAction.ChangeErrorState -> isErrorChanged(action.isLoading, action.errorState)
            is LoginOnAction.ChangeErrorStateWithMessage -> errorStateChangedWithMessage(
                action.errorState,
                action.isLoading,
                action.message
            )

            LoginOnAction.PasswordVisibilityChanged -> onPasswordVisibilityChanged()
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

    private fun signInUserWithEmailAndPassword(
        email: String,
        password: String,
        onNavigate: () -> Unit
    ) {
        viewModelScope.launch {
            repository.signInUserWithEmailAndPassword(email, password, onNavigate)
                .collect { response ->
                    when (response) {
                        is ResponseState.Loading -> {
                            _loginState.value = _loginState.value.copy(isLoading = true)
                        }

                        is ResponseState.Success -> {
                            _loginState.value = _loginState.value.copy(
                                isLoading = false,
                                isSuccess = true
                            )
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

    private fun isLoadingChanged(isLoading: Boolean) {
        _loginState.value = _loginState.value.copy(isLoading = isLoading)
    }

    private fun isErrorChanged(isError: Boolean, isLoading: Boolean) {
        _loginState.value = _loginState.value.copy(
            isError = isError,
            isLoading = isLoading
        )
    }

    private fun errorStateChangedWithMessage(
        isError: Boolean,
        isLoading: Boolean,
        errorMessage: String
    ) {
        _loginState.value = _loginState.value.copy(
            isError = isError,
            errorMessage = errorMessage,
            isLoading = isLoading
        )
    }

    private fun onPasswordVisibilityChanged() {
        _loginState.value =
            _loginState.value.copy(isPasswordVisible = !_loginState.value.isPasswordVisible)
    }
}

data class LoginUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
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
    val pagerState: Int = 1,
    var isPasswordVisible: Boolean = false,
) {
}