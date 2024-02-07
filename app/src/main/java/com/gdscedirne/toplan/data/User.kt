package com.gdscedirne.toplan.data

data class User(
    val id: Int,
    val name: String,
    val surname: String,
    val relativeName : String,
    val address: String,
    val phone: String,
    val email: String,
    val password: String
)
