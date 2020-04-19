package com.example.gowithme.data.network.token

import android.content.SharedPreferences
import com.example.gowithme.data.models.response.RefreshResponse
import com.example.gowithme.util.PreferencesConst
import com.example.gowithme.util.apiCall
import com.example.gowithme.util.Result

interface TokenRepository {

    suspend fun refreshToken(): Result<RefreshResponse>
    fun saveToken(token: String)
    fun getAccessToken(): String

}

class TokenRepositoryImpl(
    private val service: TokenService,
    private val pref: SharedPreferences
): TokenRepository {

    override suspend fun refreshToken() = apiCall {
        service.refreshToken(getRefreshToken())
    }

    override fun saveToken(token: String) {
        pref.edit().putString(PreferencesConst.ACCESS_TOKEN, token).apply()
    }

    override fun getAccessToken() = pref.getString(PreferencesConst.ACCESS_TOKEN, null) ?: ""

    private fun getRefreshToken() = pref.getString(PreferencesConst.REFRESH_TOKEN, null) ?: ""

}
