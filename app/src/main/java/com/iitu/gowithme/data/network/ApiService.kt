package com.iitu.gowithme.data.network

import com.iitu.gowithme.data.models.request.LoginRequest
import com.iitu.gowithme.data.models.response.EventResponse
import com.iitu.gowithme.data.models.response.LoginResponse
import okhttp3.ResponseBody
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