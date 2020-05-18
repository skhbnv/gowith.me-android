package com.iitu.gowithme.ui.home.model

import com.iitu.gowithme.data.models.response.EventResponse
import com.iitu.gowithme.data.network.event_list.EventListType

data class NamedEventList(
    val type: EventListType,
    val name: String,
    val events: List<EventResponse>
)