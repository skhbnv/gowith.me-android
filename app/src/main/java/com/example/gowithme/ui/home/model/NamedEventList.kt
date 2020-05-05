package com.example.gowithme.ui.home.model

import com.example.gowithme.data.models.response.EventResponse

data class NamedEventList(
    val name: String,
    val events: List<EventResponse>
)