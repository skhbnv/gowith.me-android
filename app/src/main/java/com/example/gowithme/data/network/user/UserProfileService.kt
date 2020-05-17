package com.example.gowithme.data.network.user

import com.example.gowithme.data.models.response.PagingResponse
import com.example.gowithme.data.models.response.ProfileInfoResponse
import com.example.gowithme.data.models.response.ShortUserInfo
import okhttp3.ResponseBody
import retrofit2.http.*

interface UserProfileService {

    @GET("api/v1/event/detail/{id}/subscribers")
    suspend fun getEventSubscribers(
        @Path("id") id: Int,
        @Query("page") page: Int
    ): PagingResponse<ShortUserInfo>

    @FormUrlEncoded
    @PUT("api/v1/event/detail/{id}/unsubscriber-user")
    suspend fun removeUser(
        @Path("id") id: Int,
        @Field("user_id") userId : Int
    ) : ResponseBody

    @GET("api/v1/account/me/following")
    suspend fun getMyFollowing(
        @Query("page") page: Int
    ): PagingResponse<ShortUserInfo>

    @GET("api/v1/account/me/followers")
    suspend fun getMyFollowers(
        @Query("page") page: Int
    ): PagingResponse<ShortUserInfo>

    @GET("api/v1/account/profile/{id}/followers")
    suspend fun getUserFollowers(
        @Path("id") id: Int,
        @Query("page") page: Int
    ): PagingResponse<ShortUserInfo>

    @GET("api/v1/account/profile/{id}/following")
    suspend fun getUserFollowing(
        @Path("id") id: Int,
        @Query("page") page: Int
    ): PagingResponse<ShortUserInfo>



    // ------------------------- Profile details ------------------------------------

    @GET("api/v1/account/profile/{id}")
    suspend fun getUserProfileInfo(
        @Path("id") id: Int
    ) : ProfileInfoResponse

    @POST("api/v1/account/profile/{id}/subscribe")
    suspend fun subscribeOnUser(
        @Path("id") id: Int
    ) : ResponseBody

    @POST("api/v1/account/profile/{id}/unsubscribe")
    suspend fun unSubscribeFromUser(
        @Path("id") id: Int
    ) : ResponseBody

}