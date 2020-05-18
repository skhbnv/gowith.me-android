package com.iitu.gowithme.data.network.profile

import com.iitu.gowithme.data.models.response.CreateEventImageResponse
import com.iitu.gowithme.data.models.response.EventResponse
import com.iitu.gowithme.data.models.response.PagingResponse
import com.iitu.gowithme.data.models.response.ProfileInfoResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface ProfileService {

    @GET("api/v1/account/me")
    suspend fun getMyInfo(): ProfileInfoResponse

    @GET("api/v1/account/me/viewed-events")
    suspend fun getViewEvents(
        @Query("page") page : Int
    ) : PagingResponse<EventResponse>

    @Multipart
    @POST("api/v1/files/user-image/upload")
    suspend fun uploadImage(
        @Part image: MultipartBody.Part,
        @Part("description") description: String? = null
    ): CreateEventImageResponse

}