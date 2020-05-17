package com.example.gowithme.data.models.request

data class CommentRequest(
    val content: String,
    val event: Int,
    val parent: Int? = null
)