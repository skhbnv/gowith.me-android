package com.example.gowithme.responses

data class DetailEvents(
    val author: User,
    val comments: List<Comment>,
    val creationDate: String,
    val description: String,
    val id: Int,
    val images: List<String?>,
    val subscribers: List<User>,
    val total_subscribers: String
)