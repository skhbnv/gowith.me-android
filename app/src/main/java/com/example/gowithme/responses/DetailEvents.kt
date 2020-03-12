package com.example.gowithme.responses

data class DetailEvents(
    val id: Int,
    val comments: List<Comment>,
    val description: String,
    val images: Images
)
