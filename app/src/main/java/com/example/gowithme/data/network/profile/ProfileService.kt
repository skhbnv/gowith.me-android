package com.example.gowithme.data.network.profile

import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.data.models.response.PagingResponse
import com.example.gowithme.data.models.response.ProfileInfoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProfileService {

    @GET("api/v1/account/me")
    suspend fun getMyInfo(): ProfileInfoResponse

    @GET("api/v1/account/me/viewed-events")
    suspend fun getViewEvents(
        @Query("page") page : Int
    ) : PagingResponse<EventResponse>
}