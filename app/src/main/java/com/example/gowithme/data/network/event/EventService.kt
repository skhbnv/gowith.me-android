package com.example.gowithme.data.network.event

import com.example.gowithme.data.models.request.CreateEventRequest
import com.example.gowithme.data.models.response.CategoryResponse
import com.example.gowithme.data.models.response.CreateEventResponse
import com.example.gowithme.data.models.response.EventResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface EventService {

    @GET("api/v1/event/all")
    suspend fun getEvents(): EventResponse

    @GET("api/v1/event/categories")
    suspend fun getCategories(): List<CategoryResponse>

    @POST("api/v1/event/create")
    suspend fun createEvent(
        @Body request: CreateEventRequest
    ) : CreateEventResponse

}