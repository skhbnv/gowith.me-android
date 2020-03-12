package com.example.gowithme.network

import com.example.gowithme.responses.Event
import com.example.gowithme.responses.GeneralEvents
import retrofit2.Call
import retrofit2.http.GET


interface Api {
    @GET("/api/v1/list/events/")
    fun getEvents(): Call<List<GeneralEvents>>
}