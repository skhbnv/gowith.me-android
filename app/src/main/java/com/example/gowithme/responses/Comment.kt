package com.example.gowithme.models.responses

data class Comment(
    val date: String,
    val message: String,
    val parent_comment: Any,
    val user_id: Int
)