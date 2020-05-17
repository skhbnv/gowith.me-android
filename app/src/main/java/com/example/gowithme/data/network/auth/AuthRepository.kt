package com.example.gowithme.data.network.auth

import android.content.SharedPreferences
import com.example.gowithme.data.models.request.CheckPhoneRequest
import com.example.gowithme.data.models.request.ConfirmPhoneRequest
import com.example.gowithme.data.models.request.LoginRequest
import com.example.gowithme.data.models.request.RegisterRequest
import com.example.gowithme.data.models.response.CategoryResponse
import com.example.gowithme.data.models.response.CheckPhoneResponse
import com.example.gowithme.data.models.response.LoginResponse
import com.example.gowithme.util.PreferencesConst
import com.example.gowithme.util.Result
import com.example.gowithme.util.apiCall
import okhttp3.ResponseBody

interface IAuthRepository {

    suspend fun login(request: LoginRequest): Result<LoginResponse>
    suspend fun checkPhone(request: CheckPhoneRequest): Result<CheckPhoneResponse>
    suspend fun confirmPhone(request: ConfirmPhoneRequest): Result<ResponseBody>
    suspend fun register(request: RegisterRequest): Result<LoginResponse>
    suspend fun getEventCategories(): Result<List<CategoryResponse>>

}

class AuthRepository(
    private val service: AuthService,
    private val pref: SharedPreferences
) : IAuthRepository {

    override suspend fun login(request: LoginRequest): Result<LoginResponse> {
        val result = apiCall { service.login(request) }
        if (result is Result.Success) {
            with(result.data) {
                pref.edit().putString(PreferencesConst.ACCESS_TOKEN, access).apply()
                pref.edit().putString(PreferencesConst.REFRESH_TOKEN, refresh).apply()
            }
        }
        return result
    }

    override suspend fun checkPhone(request: CheckPhoneRequest): Result<CheckPhoneResponse> = apiCall { service.checkPhone(request) }

    override suspend fun confirmPhone(request: ConfirmPhoneRequest): Result<ResponseBody> = apiCall { service.confirmPhone(request) }

    override suspend fun register(request: RegisterRequest): Result<LoginResponse> {
        val result = apiCall { service.register(request) }
        if (result is Result.Success) {
            with(result.data) {
                pref.edit().putString(PreferencesConst.ACCESS_TOKEN, access).apply()
                pref.edit().putString(PreferencesConst.REFRESH_TOKEN, refresh).apply()
            }
        }
        return result
    }

    override suspend fun getEventCategories(): Result<List<CategoryResponse>> = apiCall { service.getCategories() }

}