package com.iitu.gowithme.data.network.event

import com.iitu.gowithme.data.models.request.CommentRequest
import com.iitu.gowithme.data.models.request.CreateEventRequest
import com.iitu.gowithme.data.models.response.*
import com.iitu.gowithme.util.Result
import com.iitu.gowithme.util.apiCall
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
    suspend fun saveEvent(id: Int): Result<ResponseBody>
    suspend fun unSaveEvent(id: Int): Result<ResponseBody>

    suspend fun getEventComments(id: Int): Result<List<CommentResponse>>
    suspend fun postComment(request: CommentRequest): Result<CommentResponse>

    suspend fun getUpComingEvents() : Result<PagingResponse<EventResponse>>
    suspend fun getNewEvents() : Result<PagingResponse<EventResponse>>
    suspend fun getMostViewedEvents() : Result<PagingResponse<EventResponse>>
    suspend fun getSpecialEvents() : Result<PagingResponse<EventResponse>>
}

class EventRepository(
    private val service: EventService
) : IEventRepository {
    override suspend fun getSpecialEvents(): Result<PagingResponse<EventResponse>> = apiCall { service.getSpecialEvents() }

    override suspend fun getUpComingEvents(): Result<PagingResponse<EventResponse>> = apiCall { service.getEvents("start") }

    override suspend fun getNewEvents(): Result<PagingResponse<EventResponse>> = apiCall { service.getEvents("-created") }

    override suspend fun getMostViewedEvents(): Result<PagingResponse<EventResponse>> = apiCall { service.getEvents("-view_counter") }

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

    override suspend fun saveEvent(id: Int): Result<ResponseBody> = apiCall { service.saveEvent(id) }

    override suspend fun unSaveEvent(id: Int): Result<ResponseBody> = apiCall { service.unSaveEvent(id) }

    override suspend fun getEventComments(id: Int): Result<List<CommentResponse>> = apiCall { service.getEventComments(id) }

    override suspend fun postComment(request: CommentRequest): Result<CommentResponse> = apiCall { service.postComment(request) }

}