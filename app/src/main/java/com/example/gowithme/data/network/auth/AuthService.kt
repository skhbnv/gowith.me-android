package com.example.gowithme.data.network.auth

import com.example.gowithme.data.models.request.LoginRequest
import com.example.gowithme.data.models.response.LoginResponse
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST

interface AuthService {

    @POST("api/v1/account/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse


}