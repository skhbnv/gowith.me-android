package com.example.gowithme.data.network.event

import com.example.gowithme.data.models.request.CreateEventRequest
import com.example.gowithme.data.models.response.CategoryResponse
import com.example.gowithme.data.models.response.CreateEventResponse
import com.example.gowithme.data.models.response.CreateEventImageResponse
import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.util.Result
import com.example.gowithme.util.apiCall
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

interface IEventRepository {

    suspend fun getEvents(): Result<EventResponse>
    suspend fun getEventCategories(): Result<List<CategoryResponse>>
    suspend fun createEvent(request: CreateEventRequest): Result<CreateEventResponse>
    suspend fun uploadImage(image: File): Result<CreateEventImageResponse>

}

class EventRepository(
    private val service: EventService
) : IEventRepository {

    override suspend fun getEvents(): Result<EventResponse> = apiCall { service.getEvents() }

    override suspend fun getEventCategories(): Result<List<CategoryResponse>> = apiCall { service.getCategories() }

    override suspend fun createEvent(request: CreateEventRequest): Result<CreateEventResponse> = apiCall { service.createEvent(request) }

    override suspend fun uploadImage(image: File): Result<CreateEventImageResponse> = apiCall {
        val requestImageFile = RequestBody.create(MediaType.parse("multipart/form-data"), image)
        val imageToUpload = MultipartBody.Part.createFormData("image", image.name, requestImageFile)
        service.uploadImage(imageToUpload, image.name)
    }

}