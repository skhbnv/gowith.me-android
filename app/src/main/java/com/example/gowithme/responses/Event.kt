package com.example.gowithme.models.responses

data class Event(
    val category: List<String>,
    val comments: List<Comment>,
    val date: String,
    val description: String,
    val id: Int,
    val poster_url: String,
    val location: Location,
    val price: String,
    val title: String,
    val views: String
)