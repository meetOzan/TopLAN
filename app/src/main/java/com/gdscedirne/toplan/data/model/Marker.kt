package com.gdscedirne.toplan.data.model

data class Marker(
    val id: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val title: String = "",
    val description: String = "",
    val type: String = "",
    val date: String = "",
    val time: String = "",
    val userId: String = ""
)