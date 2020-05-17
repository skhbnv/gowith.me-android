package com.example.gowithme.data.network.auth

import com.example.gowithme.data.models.request.CheckPhoneRequest
import com.example.gowithme.data.models.request.ConfirmPhoneRequest
import com.example.gowithme.data.models.request.LoginRequest
import com.example.gowithme.data.models.request.RegisterRequest
import com.example.gowithme.data.models.response.CategoryResponse
import com.example.gowithme.data.models.response.CheckPhoneResponse
import com.example.gowithme.data.models.response.LoginResponse
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {

    @POST("api/v1/account/login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @POST("api/v1/account/check_phone")
    suspend fun checkPhone(
        @Body request: CheckPhoneRequest
    ): CheckPhoneResponse

    @POST("api/v1/account/confirm_phone")
    suspend fun confirmPhone(
        @Body request: ConfirmPhoneRequest
    ): ResponseBody

    @POST("api/v1/account/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): LoginResponse

    @GET("api/v1/event/categories")
    suspend fun getCategories(): List<CategoryResponse>
}