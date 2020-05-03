package com.example.gowithme.data.network.event_list

import androidx.paging.PagedList
import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.data.models.response.PagingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EventListService {

    @GET("api/v1/account/me/viewed-events")
    suspend fun getViewEvents(
        @Query("page") page : Int
    ) : PagingResponse<EventResponse>

    @GET("api/v1/account/me/events")
    suspend fun getMyEvents(
        @Query("page") page : Int
    ) : PagingResponse<EventResponse>

    @GET("api/v1/account/me/saved-events")
    suspend fun getSavedEvents(
        @Query("page") page : Int
    ) : PagingResponse<EventResponse>

}