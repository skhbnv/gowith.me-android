package com.example.gowithme.models.responses

data class DetailEvents(
    val author: User,
    val comments: List<Comment>,
    val creationDate: String,
    val description: String,
    val id: Int,
    val images: Images,
    val subscribers: List<User>,
    val total_subscribers: String
)