package com.gdscedirne.toplan.data.model

data class Detail(
    val name: String = "",
    val description: String = "",
    val type: String = "",
    val location: String = "",
    val date: String = "",
    val time: String = "",
    val image: String = "",
    val comment: List<UserComment> = emptyList()
)