package com.example.gowithme.data.network.event

import com.example.gowithme.data.models.request.CreateEventRequest
import com.example.gowithme.data.models.response.*
import com.example.gowithme.util.Result
import com.example.gowithme.util.apiCall
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

interface IEventRepository {

    suspend fun getEvents(): Result<PagingResponse<EventResponse>>
    suspend fun getEventCategories(): Result<List<CategoryResponse>>
    suspend fun createEvent(request: CreateEventRequest): Result<CreateEventResponse>
    suspend fun uploadImage(image: File): Result<CreateEventImageResponse>
    suspend fun getNewEvents() : Result<PagingResponse<EventResponse>>
    suspend fun getMostViewedEvents() : Result<PagingResponse<EventResponse>>
}

class EventRepository(
    private val service: EventService
) : IEventRepository {

    override suspend fun getEvents(): Result<PagingResponse<EventResponse>> = apiCall { service.getEventList() }

    override suspend fun getEventCategories(): Result<List<CategoryResponse>> = apiCall { service.getCategories() }

    override suspend fun createEvent(request: CreateEventRequest): Result<CreateEventResponse> = apiCall { service.createEvent(request) }

    override suspend fun uploadImage(image: File): Result<CreateEventImageResponse> = apiCall {
        val requestImageFile = RequestBody.create(MediaType.parse("multipart/form-data"), image)
        val imageToUpload = MultipartBody.Part.createFormData("image", image.name, requestImageFile)
        service.uploadImage(imageToUpload, image.name)
    }

    override suspend fun getNewEvents(): Result<PagingResponse<EventResponse>> = apiCall { service.getEventList(1, "created") }

    override suspend fun getMostViewedEvents(): Result<PagingResponse<EventResponse>> = apiCall { service.getEventList(1, "view_counter") }

}