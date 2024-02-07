package com.gdscedirne.toplan.presentation.login

import com.gdscedirne.toplan.data.User

sealed class LoginOnAction{
    data class SignInEmailChanged(val email: String) : LoginOnAction()
    data class SignInPasswordChanged(val password: String) : LoginOnAction()
    data class SignUpEmailChanged(val email: String) : LoginOnAction()
    data class SignUpPasswordChanged(val password: String) : LoginOnAction()
    data class SignInUser(val user: User, val onNavigate: () -> Unit) : LoginOnAction()
    data class SignUpUser(val user: User, val onNavigate: () -> Unit) : LoginOnAction()
    data class NumberChanged(val number: String) : LoginOnAction()
}