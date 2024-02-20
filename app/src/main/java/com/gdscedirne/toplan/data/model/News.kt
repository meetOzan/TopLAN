package com.gdscedirne.toplan.data.model

data class News(
    val id: String = "",
    val name: String = "",
    val author: String = "",
    val description: String = "",
    val authorImageUrl : String = "",
    val imageUrl: String = "",
    val date: String = "",
    val isImportant: Boolean = false
)