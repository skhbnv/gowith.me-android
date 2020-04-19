package com.example.gowithme.data.network.event

import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.util.Result
import com.example.gowithme.util.apiCall

interface IEventRepository {

    suspend fun getEvents(): Result<EventResponse>


}

class EventRepository(
    private val service: EventService
) : IEventRepository {

    override suspend fun getEvents(): Result<EventResponse> = apiCall { service.getEvents() }

}