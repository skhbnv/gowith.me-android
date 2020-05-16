package com.example.gowithme.data.network.event

import com.example.gowithme.data.models.request.CommentRequest
import com.example.gowithme.data.models.request.CreateEventRequest
import com.example.gowithme.data.models.response.*
import com.example.gowithme.util.Result
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.http.*

interface EventService {

    @GET("api/v1/event/all")
    suspend fun getEvents(
        @Query("ordering") ordering: String = ""
    ): PagingResponse<EventResponse>

    @GET("api/v1/event/special")
    suspend fun getSpecialEvents(): PagingResponse<EventResponse>

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

    @GET("api/v1/event/detail/{id}")
    suspend fun getEventDetails(
        @Path("id") id: Int
    ): EventResponse

    @POST("api/v1/event/{id}/subscribe")
    suspend fun subscribeOnEvent(
        @Path("id") id: Int
    ): ResponseBody

    @GET("api/v1/event/detail/{id}/comments")
    suspend fun getEventComments(
        @Path("id") id: Int
    ): List<CommentResponse>

    @POST("api/v1/comment/create")
    suspend fun postComment(
        @Body request: CommentRequest
    ): CommentResponse
}