package com.example.gowithme.data.network.token

import android.content.SharedPreferences
import com.example.gowithme.data.models.response.RefreshResponse
import com.example.gowithme.util.PreferencesConst
import com.example.gowithme.util.apiCall
import com.example.gowithme.util.Result

interface TokenRepository {

    suspend fun refreshToken(refresh: String): Result<RefreshResponse>
    fun getRefreshToken(): String
    fun saveToken(token: String)
    fun getToken(): String

}

class TokenRepositoryImpl(
    private val service: TokenService,
    private val pref: SharedPreferences
): TokenRepository {

    override suspend fun refreshToken(refresh: String) = apiCall {
        service.refreshToken(refresh)
    }

    override fun getRefreshToken() = pref.getString(PreferencesConst.REFRESH_TOKEN, null) ?: ""

    override fun saveToken(token: String) {
        pref.edit().putString(PreferencesConst.ACCESS_TOKEN, token).apply()
    }

    override fun getToken() = pref.getString(PreferencesConst.ACCESS_TOKEN, null) ?: ""

}