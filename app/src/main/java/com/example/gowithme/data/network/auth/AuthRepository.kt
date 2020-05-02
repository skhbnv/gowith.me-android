package com.example.gowithme.data.network.auth

import android.content.SharedPreferences
import com.example.gowithme.data.models.request.LoginRequest
import com.example.gowithme.data.models.response.LoginResponse
import com.example.gowithme.util.PreferencesConst
import com.example.gowithme.util.Result
import com.example.gowithme.util.apiCall

interface IAuthRepository {

    suspend fun login(request: LoginRequest): Result<LoginResponse>

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

}