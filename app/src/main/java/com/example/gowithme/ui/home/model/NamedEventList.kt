package com.example.gowithme.ui.home.model

import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.data.network.event_list.EventListType

data class NamedEventList(
    val type: EventListType,
    val name: String,
    val events: List<EventResponse>
)