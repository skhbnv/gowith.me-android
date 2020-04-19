package com.example.gowithme.data.network.event

import com.example.gowithme.data.models.response.EventResponse
import retrofit2.http.GET

interface EventService {

    @GET("api/v1/event/all")
    suspend fun getEvents(): EventResponse

}