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
    data class NameChanged(val name: String) : LoginOnAction()
    data class SurnameChanged(val surname: String) : LoginOnAction()
    data class RelativeNameChanged(val relation: String) : LoginOnAction()
    data class AddressChanged(val address: String) : LoginOnAction()
    data class PagerChanged(val position: Int) : LoginOnAction()
}