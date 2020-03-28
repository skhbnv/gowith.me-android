package com.example.gowithme.data.network.token

import com.example.gowithme.data.models.response.RefreshResponse
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface TokenService {

    @FormUrlEncoded
    @POST("api/v1/account/refresh")
    suspend fun refreshToken(
        @Field("refresh") refresh: String
    ): RefreshResponse

}