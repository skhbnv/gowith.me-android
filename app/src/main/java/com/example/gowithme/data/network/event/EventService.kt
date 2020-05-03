package com.example.gowithme.data.network.event

import com.example.gowithme.data.models.request.CreateEventRequest
import com.example.gowithme.data.models.response.*
import okhttp3.MultipartBody
import retrofit2.http.*

interface EventService {

    @GET("api/v1/event/categories")
    suspend fun getCategories(): List<CategoryResponse>

    @POST("api/v1/event/create")
    suspend fun createEvent(
        @Body request: CreateEventRequest
    ) : CreateEventResponse

    @Multipart
    @POST("api/v1/files/event-image/upload")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("description") description: String? = null
    ): CreateEventImageResponse

    @GET("api/v1/event/all")
    suspend fun getEventList(
        @Query("page") page : Int = 1,
        @Query("ordering") ordering: String = ""
    ) : PagingResponse<EventResponse>

}