package com.example.gowithme.responses

data class Comment(
    val date: String,
    val message: String,
    val parent_comment: Any,
    val user: User
)