package com.example.gowithme.data.network.event

import com.example.gowithme.data.models.request.CreateEventRequest
import com.example.gowithme.data.models.response.*
import com.example.gowithme.util.Result
import com.example.gowithme.util.apiCall
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.io.File

interface IEventRepository {

    suspend fun getEvents(): Result<PagingResponse<EventResponse>>
    suspend fun getEventCategories(): Result<List<CategoryResponse>>
    suspend fun createEvent(request: CreateEventRequest): Result<CreateEventResponse>
    suspend fun uploadImage(image: File): Result<CreateEventImageResponse>
    suspend fun getEventDetails(id: Int): Result<EventResponse>
    suspend fun subscribeOnEvent(id: Int): Result<ResponseBody>
}

class EventRepository(
    private val service: EventService
) : IEventRepository {

    override suspend fun getEvents(): Result<PagingResponse<EventResponse>> = apiCall { service.getEvents() }

    override suspend fun getEventCategories(): Result<List<CategoryResponse>> = apiCall { service.getCategories() }

    override suspend fun createEvent(request: CreateEventRequest): Result<CreateEventResponse> = apiCall { service.createEvent(request) }

    override suspend fun uploadImage(image: File): Result<CreateEventImageResponse> = apiCall {
        val requestImageFile = RequestBody.create(MediaType.parse("multipart/form-data"), image)
        val imageToUpload = MultipartBody.Part.createFormData("image", image.name, requestImageFile)
        service.uploadImage(imageToUpload, image.name)
    }

    override suspend fun getEventDetails(id: Int): Result<EventResponse> = apiCall { service.getEventDetails(id) }

    override suspend fun subscribeOnEvent(id: Int): Result<ResponseBody> = apiCall { service.subscribeOnEvent(id) }

}