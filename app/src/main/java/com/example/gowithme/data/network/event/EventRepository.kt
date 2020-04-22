package com.example.gowithme.data.network.event

import com.example.gowithme.data.models.request.CreateEventRequest
import com.example.gowithme.data.models.response.CategoryResponse
import com.example.gowithme.data.models.response.CreateEventResponse
import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.util.Result
import com.example.gowithme.util.apiCall

interface IEventRepository {

    suspend fun getEvents(): Result<EventResponse>
    suspend fun getEventCategories(): Result<List<CategoryResponse>>
    suspend fun createEvent(request: CreateEventRequest): Result<CreateEventResponse>

}

class EventRepository(
    private val service: EventService
) : IEventRepository {

    override suspend fun getEvents(): Result<EventResponse> = apiCall { service.getEvents() }

    override suspend fun getEventCategories(): Result<List<CategoryResponse>> = apiCall { service.getCategories() }

    override suspend fun createEvent(request: CreateEventRequest): Result<CreateEventResponse> = apiCall { service.createEvent(request) }

}