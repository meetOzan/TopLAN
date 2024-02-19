package com.gdscedirne.toplan.data.model

data class Feed(
    val id: String = "",
    val title: String = "",
    val imageUrl: String = "",
    val user: String = "",
    val description: String = "",
    val likeCount: Long = 0,
    val comments: List<String> = emptyList()
 )
