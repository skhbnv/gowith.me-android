package com.iitu.gowithme.data.network.token

import com.iitu.gowithme.data.models.response.RefreshResponse
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