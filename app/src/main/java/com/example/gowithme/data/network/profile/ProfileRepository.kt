package com.example.gowithme.data.network.profile

import com.example.gowithme.data.models.response.CreateEventImageResponse
import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.data.models.response.ProfileInfoResponse
import com.example.gowithme.data.models.response.PagingResponse
import com.example.gowithme.util.Result
import com.example.gowithme.util.apiCall
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

interface IProfileRepository {

    suspend fun getMyInfo(): Result<ProfileInfoResponse>
    suspend fun getViewEvents(page: Int = 1) : Result<PagingResponse<EventResponse>>

    suspend fun uploadProfileImage(image: File): Result<CreateEventImageResponse>

}

class ProfileRepository(
    private val service: ProfileService
) : IProfileRepository {
    override suspend fun getMyInfo(): Result<ProfileInfoResponse> = apiCall { service.getMyInfo() }

    override suspend fun getViewEvents(page: Int): Result<PagingResponse<EventResponse>> = apiCall { service.getViewEvents(page) }

    override suspend fun uploadProfileImage(image: File): Result<CreateEventImageResponse> = apiCall {
        val requestImageFile = RequestBody.create(MediaType.parse("multipart/form-data"), image)
        val imageToUpload = MultipartBody.Part.createFormData("image", image.name, requestImageFile)
        service.uploadImage(imageToUpload, image.name)
    }
}
