package com.example.gowithme.data.network

import com.example.gowithme.data.models.request.LoginRequest
import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.data.models.response.LoginResponse
import com.example.gowithme.responses.GeneralEvents
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @GET("api/v1/event/all")
    suspend fun getEvents(
        @Header("Authorization") auth: String = "Bearer asdasda"
    ): List<EventResponse>

    @POST("api/v1/account/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("api/v1/account/refresh")
    suspend fun refreshToken(
        @Field("refresh") refresh: String
    ): ResponseBody
}