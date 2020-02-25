package com.example.gowithme.responses

data class Event(
    val category: List<String>,
    val comments: List<Comment>,
    val date: String,
    val description: String,
    val id: Int,
    val images: Images,
    val location: Location,
    val price: String,
    val title: String
)