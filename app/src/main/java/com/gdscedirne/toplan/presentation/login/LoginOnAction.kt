package com.gdscedirne.toplan.presentation.login

sealed class LoginOnAction{
    data class EmailChanged(val email: String) : LoginOnAction()
    data class PasswordChanged(val password: String) : LoginOnAction()
    data class NumberChanged(val number: String) : LoginOnAction()
}