package com.example.gowithme.data.network.user

import com.example.gowithme.data.models.response.PagingResponse
import com.example.gowithme.data.models.response.ProfileInfoResponse
import com.example.gowithme.data.models.response.ShortUserInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface UserProfileService {

    @GET("api/v1/event/detail/{id}/subscribers")
    suspend fun getEventSubscribers(
        @Path("id") id: Int,
        @Query("page") page: Int
    ): PagingResponse<ShortUserInfo>

    @GET("api/v1/account/profile/{id}")
    suspend fun getUserProfileInfo(
        @Path("id") id: Int
    ) : ProfileInfoResponse

}