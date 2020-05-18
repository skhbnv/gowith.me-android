package com.iitu.gowithme.data.network.token

import android.content.SharedPreferences
import android.util.Log
import com.iitu.gowithme.data.models.response.RefreshResponse
import com.iitu.gowithme.util.PreferencesConst
import com.iitu.gowithme.util.apiCall
import com.iitu.gowithme.util.Result

interface TokenRepository {

    suspend fun refreshToken(): Result<RefreshResponse>
    fun saveToken(token: String)
    fun getAccessToken(): String
    fun removeTokens()

}

class TokenRepositoryImpl(
    private val service: TokenService,
    private val pref: SharedPreferences
): TokenRepository {

    override suspend fun refreshToken() = apiCall {
        Log.d("taaag", "getRefreshToken() = ${getRefreshToken()}")
        service.refreshToken(getRefreshToken())
    }

    override fun saveToken(token: String) {
        pref.edit().putString(PreferencesConst.ACCESS_TOKEN, token).apply()
    }

    override fun getAccessToken() = pref.getString(PreferencesConst.ACCESS_TOKEN, null) ?: ""

    override fun removeTokens() {
        pref.edit().putString(PreferencesConst.ACCESS_TOKEN, "").apply()
        pref.edit().putString(PreferencesConst.REFRESH_TOKEN, "").apply()
    }

    private fun getRefreshToken() = pref.getString(PreferencesConst.REFRESH_TOKEN, null) ?: ""

}
