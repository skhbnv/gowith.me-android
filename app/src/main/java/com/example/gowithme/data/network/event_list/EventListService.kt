package com.example.gowithme.data.network.event_list

import com.example.gowithme.data.models.response.EventResponse
import com.example.gowithme.data.models.response.PagingResponse
import retrofit2.http.GET
import retrofit2.http.Path
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

    @GET("api/v1/event/special")
    suspend fun getSpecialEvents(
        @Query("page") page : Int
    ): PagingResponse<EventResponse>

    @GET("api/v1/event/all")
    suspend fun getEvents(
        @Query("page") page : Int,
        @Query("ordering") ordering: String = ""
    ): PagingResponse<EventResponse>

    @GET("api/v1/account/profile/{id}/events")
    suspend fun getUserEvents(
        @Path("id") id: Int,
        @Query("page") page : Int
    ): PagingResponse<EventResponse>

    @GET("api/v1/account/me/following/events")
    suspend fun getFollowingEvents(
        @Query("page") page : Int
    ): PagingResponse<EventResponse>

}