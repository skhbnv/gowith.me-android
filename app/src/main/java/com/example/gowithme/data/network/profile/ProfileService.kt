package com.example.gowithme.data.network.profile

import com.example.gowithme.data.models.response.MyInfoResponse
import retrofit2.http.GET

interface ProfileService {

    @GET("api/v1/account/me")
    suspend fun getMyInfo(): MyInfoResponse

}