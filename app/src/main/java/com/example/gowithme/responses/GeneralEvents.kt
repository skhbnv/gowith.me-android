package com.example.gowithme.responses

import java.io.Serializable

data class GeneralEvents(
    val category: List<String>,
    val date: String,
    val id: Int,
    val location: Location,
    val poster_url: String,
    val price: String,
    val title: String,
    val views: String,
    val subscribers: Int,
    val message: String,
    val countDownTime: String
) : Serializable